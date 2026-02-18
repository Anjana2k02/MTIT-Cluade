function CustomerDetailsStep({ data, onChange }) {
  const handleChange = (e) => {
    const { name, value } = e.target;
    onChange('customer', name, value);
  };

  const isCorporate = data.customerType === 'CORPORATE';

  return (
    <div className="intake-step">
      <h3 className="intake-section-title">Customer Details</h3>

      <div className="form-row">
        <div className="form-group">
          <label>Full Name *</label>
          <input type="text" name="fullName" value={data.fullName} onChange={handleChange} required maxLength={100} placeholder="Enter full name" />
        </div>
        <div className="form-group">
          <label>NIC / National ID *</label>
          <input type="text" name="nicNumber" value={data.nicNumber} onChange={handleChange} required pattern="^(\d{9}[VvXx]|\d{12})$" placeholder="e.g., 200012345678 or 901234567V" />
        </div>
      </div>

      <div className="form-row">
        <div className="form-group">
          <label>Primary Contact *</label>
          <input type="tel" name="primaryContact" value={data.primaryContact} onChange={handleChange} required pattern="^\+?[0-9]{10,15}$" placeholder="+94XXXXXXXXX" />
        </div>
        <div className="form-group">
          <label>Secondary Contact</label>
          <input type="tel" name="secondaryContact" value={data.secondaryContact} onChange={handleChange} placeholder="+94XXXXXXXXX" />
        </div>
      </div>

      <div className="form-row">
        <div className="form-group">
          <label>Email *</label>
          <input type="email" name="email" value={data.email} onChange={handleChange} required placeholder="email@example.com" />
        </div>
        <div className="form-group">
          <label>Customer Type *</label>
          <select name="customerType" value={data.customerType} onChange={handleChange} required>
            <option value="">Select Type</option>
            <option value="INDIVIDUAL">Individual</option>
            <option value="CORPORATE">Corporate</option>
          </select>
        </div>
      </div>

      <div className="form-row three-col">
        <div className="form-group">
          <label>Street</label>
          <input type="text" name="street" value={data.street} onChange={handleChange} placeholder="Street address" />
        </div>
        <div className="form-group">
          <label>City</label>
          <input type="text" name="city" value={data.city} onChange={handleChange} placeholder="City" />
        </div>
        <div className="form-group">
          <label>Postal Code</label>
          <input type="text" name="postalCode" value={data.postalCode} onChange={handleChange} maxLength={10} placeholder="Postal code" />
        </div>
      </div>

      {isCorporate && (
        <div className="corporate-fields">
          <h4>Corporate Information</h4>
          <div className="form-row">
            <div className="form-group">
              <label>Company Registration Number *</label>
              <input type="text" name="companyRegistrationNumber" value={data.companyRegistrationNumber} onChange={handleChange} required placeholder="Company registration number" />
            </div>
            <div className="form-group">
              <label>Tax Identification Number *</label>
              <input type="text" name="taxId" value={data.taxId} onChange={handleChange} required placeholder="Tax ID" />
            </div>
          </div>
        </div>
      )}

      <div className="form-row">
        <div className="form-group">
          <label>Customer Since</label>
          <input type="date" name="customerSince" value={data.customerSince} onChange={handleChange} max={new Date().toISOString().split('T')[0]} />
        </div>
        <div className="form-group">
          <label>Preferred Contact Method</label>
          <select name="preferredContactMethod" value={data.preferredContactMethod} onChange={handleChange}>
            <option value="">Select Method</option>
            <option value="PHONE">Phone</option>
            <option value="EMAIL">Email</option>
            <option value="SMS">SMS</option>
          </select>
        </div>
      </div>

      <div className="form-row">
        <div className="form-group">
          <label>Emergency Contact Name</label>
          <input type="text" name="emergencyContactName" value={data.emergencyContactName} onChange={handleChange} placeholder="Emergency contact name" />
        </div>
        <div className="form-group">
          <label>Emergency Contact Phone</label>
          <input type="tel" name="emergencyContactPhone" value={data.emergencyContactPhone} onChange={handleChange} placeholder="+94XXXXXXXXX" />
        </div>
      </div>
    </div>
  );
}

export default CustomerDetailsStep;
