import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getIntakeById } from '../services/intakeService';

function IntakeDetailPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [record, setRecord] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchRecord = async () => {
      try {
        const res = await getIntakeById(id);
        setRecord(res.data);
      } catch (err) {
        setError(err.response?.data?.message || 'Failed to load record details.');
      } finally {
        setLoading(false);
      }
    };
    fetchRecord();
  }, [id]);

  const formatDate = (dateStr) => {
    if (!dateStr) return '-';
    return new Date(dateStr).toLocaleDateString('en-US', {
      year: 'numeric', month: 'short', day: 'numeric'
    });
  };

  const formatDateTime = (dateStr) => {
    if (!dateStr) return '-';
    return new Date(dateStr).toLocaleString('en-US', {
      year: 'numeric', month: 'short', day: 'numeric',
      hour: '2-digit', minute: '2-digit'
    });
  };

  const formatCurrency = (amount) => {
    if (amount === null || amount === undefined) return '-';
    return `LKR ${parseFloat(amount).toLocaleString('en-US', { minimumFractionDigits: 2 })}`;
  };

  const getPriorityClass = (priority) => {
    switch (priority) {
      case 'URGENT': return 'priority-urgent';
      case 'HIGH': return 'priority-high';
      case 'NORMAL': return 'priority-normal';
      case 'LOW': return 'priority-low';
      default: return '';
    }
  };

  if (loading) {
    return <div className="loading">Loading record details...</div>;
  }

  if (error) {
    return (
      <div className="detail-page">
        <div className="error-alert">{error}</div>
        <button className="btn-secondary" onClick={() => navigate('/records')}>Back to Records</button>
      </div>
    );
  }

  if (!record) return null;

  const { customer, vehicle, service, internal } = record;

  return (
    <div className="detail-page">
      <div className="detail-header">
        <div>
          <h2>Service Record Details</h2>
          <div className="detail-wo">
            <span className="wo-badge">{record.workOrderNumber}</span>
            <span className="detail-date">Intake: {formatDateTime(record.intakeTimestamp)}</span>
          </div>
        </div>
        <button className="btn-secondary" onClick={() => navigate('/records')}>Back to Records</button>
      </div>

      {/* Customer Details */}
      <div className="detail-section">
        <h3 className="section-title">Customer Details</h3>
        <div className="detail-grid">
          <div className="detail-field">
            <label>Full Name</label>
            <span>{customer?.fullName || '-'}</span>
          </div>
          <div className="detail-field">
            <label>NIC Number</label>
            <span>{customer?.nicNumber || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Customer Type</label>
            <span>{customer?.customerType?.replace(/_/g, ' ') || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Primary Contact</label>
            <span>{customer?.primaryContact || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Secondary Contact</label>
            <span>{customer?.secondaryContact || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Email</label>
            <span>{customer?.email || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Preferred Contact</label>
            <span>{customer?.preferredContactMethod?.replace(/_/g, ' ') || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Address</label>
            <span>{[customer?.street, customer?.city, customer?.postalCode].filter(Boolean).join(', ') || '-'}</span>
          </div>
          {customer?.customerType === 'CORPORATE' && (
            <>
              <div className="detail-field">
                <label>Company Reg. No.</label>
                <span>{customer?.companyRegistrationNumber || '-'}</span>
              </div>
              <div className="detail-field">
                <label>Tax ID</label>
                <span>{customer?.taxId || '-'}</span>
              </div>
            </>
          )}
          <div className="detail-field">
            <label>Customer Since</label>
            <span>{formatDate(customer?.customerSince)}</span>
          </div>
          <div className="detail-field">
            <label>Emergency Contact</label>
            <span>{customer?.emergencyContactName ? `${customer.emergencyContactName} (${customer.emergencyContactPhone || ''})` : '-'}</span>
          </div>
        </div>
      </div>

      {/* Vehicle Details */}
      <div className="detail-section">
        <h3 className="section-title">Vehicle Details</h3>
        <div className="detail-grid">
          <div className="detail-field">
            <label>Vehicle Name</label>
            <span>{vehicle?.vehicleName || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Registration No.</label>
            <span>{vehicle?.registrationNumber || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Chassis Number</label>
            <span>{vehicle?.chassisNumber || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Engine Number</label>
            <span>{vehicle?.engineNumber || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Brand / Model</label>
            <span>{[vehicle?.brand, vehicle?.model].filter(Boolean).join(' ') || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Year</label>
            <span>{vehicle?.manufacturingYear || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Vehicle Type</label>
            <span>{vehicle?.vehicleType?.replace(/_/g, ' ') || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Fuel Type</label>
            <span>{vehicle?.fuelType?.replace(/_/g, ' ') || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Transmission</label>
            <span>{vehicle?.transmissionType?.replace(/_/g, ' ') || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Mileage</label>
            <span>{vehicle?.mileage ? `${vehicle.mileage.toLocaleString()} km` : '-'}</span>
          </div>
          <div className="detail-field">
            <label>Color</label>
            <span>{vehicle?.color || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Insurance Provider</label>
            <span>{vehicle?.insuranceProvider || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Insurance Expiry</label>
            <span>{formatDate(vehicle?.insuranceExpiryDate)}</span>
          </div>
          <div className="detail-field">
            <label>Warranty Status</label>
            <span>{vehicle?.warrantyStatus?.replace(/_/g, ' ') || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Last Service Date</label>
            <span>{formatDate(vehicle?.lastServiceDate)}</span>
          </div>
        </div>
      </div>

      {/* Service Details */}
      <div className="detail-section">
        <h3 className="section-title">Service Details</h3>
        <div className="detail-grid">
          <div className="detail-field">
            <label>Service Type</label>
            <span>{service?.serviceType?.replace(/_/g, ' ') || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Priority</label>
            <span className={`priority-badge ${getPriorityClass(service?.servicePriority)}`}>
              {service?.servicePriority || '-'}
            </span>
          </div>
          <div className="detail-field">
            <label>Assigned Technician</label>
            <span>{service?.assignedTechnician || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Estimated Budget</label>
            <span>{formatCurrency(service?.estimatedBudget)}</span>
          </div>
          <div className="detail-field">
            <label>Expected Completion</label>
            <span>{formatDate(service?.expectedCompletionDate)}</span>
          </div>
          <div className="detail-field">
            <label>Approval Required</label>
            <span>{service?.customerApprovalRequired ? 'Yes' : 'No'}</span>
          </div>
          {service?.customerApprovalRequired && (
            <div className="detail-field">
              <label>Approval Status</label>
              <span>{service?.approvalStatus?.replace(/_/g, ' ') || '-'}</span>
            </div>
          )}
          <div className="detail-field">
            <label>Agreement Confirmed</label>
            <span>{service?.serviceAgreementConfirmed ? 'Yes' : 'No'}</span>
          </div>
          <div className="detail-field full-width">
            <label>Problem Description</label>
            <span className="description-text">{service?.problemDescription || '-'}</span>
          </div>
        </div>

        {/* Cost Breakdown */}
        <div className="cost-summary">
          <div className="cost-row">
            <span>Labor Cost</span>
            <span>{formatCurrency(service?.laborCost)}</span>
          </div>
          <div className="cost-row">
            <span>Parts Cost</span>
            <span>{formatCurrency(service?.partsCost)}</span>
          </div>
          <div className="cost-row cost-total">
            <span>Total Cost</span>
            <span>{formatCurrency(service?.totalCost)}</span>
          </div>
        </div>

        {/* Parts List */}
        {service?.partsRequired && service.partsRequired.length > 0 && (
          <div className="parts-detail">
            <h4>Parts Required</h4>
            <table className="parts-table">
              <thead>
                <tr>
                  <th>Part Name</th>
                  <th>Part Number</th>
                  <th>Qty</th>
                  <th>Unit Price</th>
                </tr>
              </thead>
              <tbody>
                {service.partsRequired.map((part, idx) => (
                  <tr key={idx}>
                    <td>{part.partName || '-'}</td>
                    <td>{part.partNumber || '-'}</td>
                    <td>{part.quantity || '-'}</td>
                    <td>{formatCurrency(part.unitPrice)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {/* Internal Details */}
      <div className="detail-section">
        <h3 className="section-title">Internal / Company Details</h3>
        <div className="detail-grid">
          <div className="detail-field">
            <label>Branch Location</label>
            <span>{internal?.branchLocation || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Service Advisor</label>
            <span>{internal?.serviceAdvisor || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Custodian</label>
            <span>{internal?.custodian || '-'}</span>
          </div>
          <div className="detail-field">
            <label>Payment Method</label>
            <span>{internal?.paymentMethod?.replace(/_/g, ' ') || '-'}</span>
          </div>
          <div className="detail-field">
            <label>VAT %</label>
            <span>{internal?.vatPercentage != null ? `${internal.vatPercentage}%` : '-'}</span>
          </div>
          <div className="detail-field">
            <label>Discount %</label>
            <span>{internal?.discountPercentage != null ? `${internal.discountPercentage}%` : '-'}</span>
          </div>
          <div className="detail-field">
            <label>Final Payable Amount</label>
            <span className="amount-highlight">{formatCurrency(internal?.finalPayableAmount)}</span>
          </div>
          {internal?.internalNotes && (
            <div className="detail-field full-width">
              <label>Internal Notes</label>
              <span className="description-text">{internal.internalNotes}</span>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default IntakeDetailPage;
