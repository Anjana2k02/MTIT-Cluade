import { useState } from 'react';
import StepIndicator from './StepIndicator';
import CustomerDetailsStep from './CustomerDetailsStep';
import VehicleDetailsStep from './VehicleDetailsStep';
import ServiceDetailsStep from './ServiceDetailsStep';
import InternalDetailsStep from './InternalDetailsStep';
import IntakeConfirmation from './IntakeConfirmation';
import { submitIntakeForm } from '../../services/intakeService';

const initialFormState = {
  customer: {
    fullName: '', nicNumber: '', primaryContact: '', secondaryContact: '',
    email: '', street: '', city: '', postalCode: '',
    customerType: '', companyRegistrationNumber: '', taxId: '',
    customerSince: '', preferredContactMethod: '',
    emergencyContactName: '', emergencyContactPhone: ''
  },
  vehicle: {
    vehicleName: '', registrationNumber: '', chassisNumber: '', engineNumber: '',
    brand: '', model: '', manufacturingYear: '', vehicleType: '',
    fuelType: '', transmissionType: '', mileage: '', color: '',
    insuranceProvider: '', insuranceExpiryDate: '', warrantyStatus: '', lastServiceDate: ''
  },
  service: {
    serviceType: '', servicePriority: '', problemDescription: '',
    estimatedBudget: '', expectedCompletionDate: '', assignedTechnician: '',
    partsRequired: [], laborCost: '', partsCost: '',
    customerApprovalRequired: false, approvalStatus: '',
    serviceAgreementConfirmed: false
  },
  internal: {
    branchLocation: '', serviceAdvisor: '', custodian: '',
    paymentMethod: '', vatPercentage: '', discountPercentage: '',
    internalNotes: ''
  }
};

function IntakeFormWizard() {
  const [formData, setFormData] = useState(initialFormState);
  const [currentStep, setCurrentStep] = useState(0);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [fieldErrors, setFieldErrors] = useState({});
  const [submitted, setSubmitted] = useState(false);
  const [response, setResponse] = useState(null);

  const updateFormData = (section, field, value) => {
    setFormData(prev => ({
      ...prev,
      [section]: {
        ...prev[section],
        [field]: value
      }
    }));
    // Clear field error when user changes the value
    if (fieldErrors[`${section}.${field}`]) {
      setFieldErrors(prev => {
        const updated = { ...prev };
        delete updated[`${section}.${field}`];
        return updated;
      });
    }
  };

  const validateStep = (step) => {
    switch (step) {
      case 0: {
        const c = formData.customer;
        if (!c.fullName || !c.nicNumber || !c.primaryContact || !c.email || !c.customerType) {
          setError('Please fill in all required customer fields.');
          return false;
        }
        if (!/^(\d{9}[VvXx]|\d{12})$/.test(c.nicNumber)) {
          setError('NIC must be 9 digits followed by V/X or 12 digits.');
          return false;
        }
        if (c.customerType === 'CORPORATE' && (!c.companyRegistrationNumber || !c.taxId)) {
          setError('Company Registration Number and Tax ID are required for corporate customers.');
          return false;
        }
        break;
      }
      case 1: {
        const v = formData.vehicle;
        if (!v.vehicleName || !v.registrationNumber || !v.chassisNumber || !v.brand || !v.model || !v.manufacturingYear || !v.vehicleType || !v.fuelType || !v.transmissionType || !v.mileage) {
          setError('Please fill in all required vehicle fields.');
          return false;
        }
        if (parseInt(v.manufacturingYear) > new Date().getFullYear()) {
          setError('Manufacturing year cannot be in the future.');
          return false;
        }
        if (parseFloat(v.mileage) <= 0) {
          setError('Mileage must be positive.');
          return false;
        }
        break;
      }
      case 2: {
        const s = formData.service;
        if (!s.serviceType || !s.servicePriority || !s.problemDescription || !s.estimatedBudget || !s.expectedCompletionDate) {
          setError('Please fill in all required service fields.');
          return false;
        }
        if (s.problemDescription.length < 50) {
          setError('Problem description must be at least 50 characters.');
          return false;
        }
        if (parseFloat(s.estimatedBudget) <= 0) {
          setError('Estimated budget must be positive.');
          return false;
        }
        break;
      }
      default:
        break;
    }
    setError(null);
    return true;
  };

  const handleNext = () => {
    if (validateStep(currentStep)) {
      setCurrentStep(prev => prev + 1);
    }
  };

  const handlePrev = () => {
    setError(null);
    setCurrentStep(prev => prev - 1);
  };

  const cleanFormData = (data) => {
    // Fields that must be sent as numbers to match backend types
    const numericFields = new Set([
      'manufacturingYear', 'mileage', 'estimatedBudget',
      'laborCost', 'partsCost', 'vatPercentage', 'discountPercentage',
      'quantity', 'unitPrice'
    ]);

    const clean = (obj) => {
      const result = {};
      for (const [key, value] of Object.entries(obj)) {
        if (value === '' || value === null || value === undefined) {
          continue; // Skip empty — backend receives null (valid for optional fields)
        }
        if (Array.isArray(value)) {
          result[key] = value.map(item =>
            typeof item === 'object' ? clean(item) : item
          );
        } else if (numericFields.has(key)) {
          const num = Number(value);
          if (!isNaN(num)) result[key] = num;
        } else {
          result[key] = value;
        }
      }
      return result;
    };

    const customerPayload = clean(data.customer);
    const vehiclePayload = clean(data.vehicle);

    const servicePayload = clean(data.service);
    // Booleans must always be sent (even if false)
    servicePayload.customerApprovalRequired = !!data.service.customerApprovalRequired;
    servicePayload.serviceAgreementConfirmed = !!data.service.serviceAgreementConfirmed;
    // Only send partsRequired if there are parts
    if (data.service.partsRequired.length > 0) {
      servicePayload.partsRequired = data.service.partsRequired.map(p => clean(p));
    }

    // Only send internal section if any field has a value
    const hasInternal = Object.entries(data.internal).some(
      ([, v]) => v !== '' && v !== null && v !== undefined
    );
    const internalPayload = hasInternal ? clean(data.internal) : null;

    return {
      customer: customerPayload,
      vehicle: vehiclePayload,
      service: servicePayload,
      internal: internalPayload
    };
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateStep(currentStep)) return;

    setLoading(true);
    setError(null);
    setFieldErrors({});

    try {
      const payload = cleanFormData(formData);
      const res = await submitIntakeForm(payload);
      setResponse(res.data);
      setSubmitted(true);
    } catch (err) {
      if (err.response?.data?.fieldErrors) {
        setFieldErrors(err.response.data.fieldErrors);
        setError('Please fix the validation errors below.');
      } else {
        setError(err.response?.data?.message || 'Failed to submit intake form. Please try again.');
      }
    } finally {
      setLoading(false);
    }
  };

  const handleReset = () => {
    setFormData(initialFormState);
    setCurrentStep(0);
    setSubmitted(false);
    setResponse(null);
    setError(null);
    setFieldErrors({});
  };

  if (submitted && response) {
    return <IntakeConfirmation response={response} onReset={handleReset} />;
  }

  return (
    <div className="intake-form-card">
      <StepIndicator currentStep={currentStep} />

      {error && <div className="error-alert">{error}</div>}

      {Object.keys(fieldErrors).length > 0 && (
        <div className="field-errors-alert">
          <ul>
            {Object.entries(fieldErrors).map(([field, message]) => (
              <li key={field}><strong>{field}:</strong> {message}</li>
            ))}
          </ul>
        </div>
      )}

      <form onSubmit={handleSubmit}>
        {currentStep === 0 && <CustomerDetailsStep data={formData.customer} onChange={updateFormData} />}
        {currentStep === 1 && <VehicleDetailsStep data={formData.vehicle} onChange={updateFormData} />}
        {currentStep === 2 && <ServiceDetailsStep data={formData.service} onChange={updateFormData} />}
        {currentStep === 3 && <InternalDetailsStep data={formData.internal} serviceData={formData.service} onChange={updateFormData} />}

        <div className="form-navigation">
          {currentStep > 0 && (
            <button type="button" className="btn-prev" onClick={handlePrev}>Previous</button>
          )}
          {currentStep === 0 && <div />}

          {currentStep < 3 ? (
            <button type="button" className="btn-next" onClick={handleNext}>Next</button>
          ) : (
            <button type="submit" className="btn-submit" disabled={loading}>
              {loading ? 'Submitting...' : 'Submit Intake Form'}
            </button>
          )}
        </div>
      </form>
    </div>
  );
}

export default IntakeFormWizard;
