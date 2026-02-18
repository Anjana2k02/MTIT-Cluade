function ServiceDetailsStep({ data, onChange }) {
  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    onChange('service', name, type === 'checkbox' ? checked : value);
  };

  const handlePartChange = (index, field, value) => {
    const updated = [...data.partsRequired];
    updated[index] = { ...updated[index], [field]: value };
    onChange('service', 'partsRequired', updated);
  };

  const addPart = () => {
    const updated = [...data.partsRequired, { partName: '', partNumber: '', quantity: 1, unitPrice: 0 }];
    onChange('service', 'partsRequired', updated);
  };

  const removePart = (index) => {
    const updated = data.partsRequired.filter((_, i) => i !== index);
    onChange('service', 'partsRequired', updated);
  };

  const today = new Date().toISOString().split('T')[0];
  const laborCost = parseFloat(data.laborCost) || 0;
  const partsCost = parseFloat(data.partsCost) || 0;
  const totalCost = laborCost + partsCost;

  return (
    <div className="intake-step">
      <h3 className="intake-section-title">Service Details</h3>

      <div className="form-row">
        <div className="form-group">
          <label>Service Type *</label>
          <select name="serviceType" value={data.serviceType} onChange={handleChange} required>
            <option value="">Select Service Type</option>
            <option value="FULL_SERVICE">Full Service</option>
            <option value="MINOR_SERVICE">Minor Service</option>
            <option value="MAJOR_REPAIR">Major Repair</option>
            <option value="BODY_WORK">Body Work</option>
            <option value="ELECTRICAL">Electrical</option>
            <option value="DIAGNOSTICS">Diagnostics</option>
            <option value="TIRE_SERVICE">Tire Service</option>
            <option value="OIL_CHANGE">Oil Change</option>
            <option value="INSPECTION">Inspection</option>
            <option value="OTHER">Other</option>
          </select>
        </div>
        <div className="form-group">
          <label>Service Priority *</label>
          <select name="servicePriority" value={data.servicePriority} onChange={handleChange} required>
            <option value="">Select Priority</option>
            <option value="LOW">Low</option>
            <option value="NORMAL">Normal</option>
            <option value="HIGH">High</option>
            <option value="URGENT">Urgent</option>
          </select>
        </div>
      </div>

      <div className="form-group">
        <label>Problem Description * <span className="char-count">({(data.problemDescription || '').length}/50 min)</span></label>
        <textarea
          name="problemDescription"
          value={data.problemDescription}
          onChange={handleChange}
          required
          minLength={50}
          maxLength={2000}
          rows={4}
          placeholder="Describe the problem in detail (minimum 50 characters)..."
        />
      </div>

      <div className="form-row">
        <div className="form-group">
          <label>Estimated Budget (LKR) *</label>
          <input type="number" name="estimatedBudget" value={data.estimatedBudget} onChange={handleChange} required min={0.01} step="0.01" placeholder="0.00" />
        </div>
        <div className="form-group">
          <label>Expected Completion Date *</label>
          <input type="date" name="expectedCompletionDate" value={data.expectedCompletionDate} onChange={handleChange} required min={today} />
        </div>
      </div>

      <div className="form-group">
        <label>Assigned Technician</label>
        <input type="text" name="assignedTechnician" value={data.assignedTechnician} onChange={handleChange} placeholder="Technician name" />
      </div>

      {/* Dynamic Parts List */}
      <div className="parts-list">
        <div className="parts-header">
          <h4>Parts Required</h4>
          <button type="button" className="add-part-btn" onClick={addPart}>+ Add Part</button>
        </div>

        {data.partsRequired.map((part, index) => (
          <div key={index} className="part-row">
            <div className="form-group">
              <label>Part Name *</label>
              <input type="text" value={part.partName} onChange={(e) => handlePartChange(index, 'partName', e.target.value)} required placeholder="Part name" />
            </div>
            <div className="form-group">
              <label>Part #</label>
              <input type="text" value={part.partNumber} onChange={(e) => handlePartChange(index, 'partNumber', e.target.value)} placeholder="Part number" />
            </div>
            <div className="form-group">
              <label>Qty *</label>
              <input type="number" value={part.quantity} onChange={(e) => handlePartChange(index, 'quantity', parseInt(e.target.value) || 1)} min={1} />
            </div>
            <div className="form-group">
              <label>Unit Price</label>
              <input type="number" value={part.unitPrice} onChange={(e) => handlePartChange(index, 'unitPrice', parseFloat(e.target.value) || 0)} min={0} step="0.01" />
            </div>
            <button type="button" className="remove-part-btn" onClick={() => removePart(index)} title="Remove part">&times;</button>
          </div>
        ))}

        {data.partsRequired.length === 0 && (
          <p className="no-parts-text">No parts added yet. Click "Add Part" to add parts.</p>
        )}
      </div>

      <div className="form-row three-col">
        <div className="form-group">
          <label>Labor Cost (LKR)</label>
          <input type="number" name="laborCost" value={data.laborCost} onChange={handleChange} min={0} step="0.01" placeholder="0.00" />
        </div>
        <div className="form-group">
          <label>Parts Cost (LKR)</label>
          <input type="number" name="partsCost" value={data.partsCost} onChange={handleChange} min={0} step="0.01" placeholder="0.00" />
        </div>
        <div className="form-group">
          <label>Total Cost (LKR)</label>
          <div className="calculated-field">{totalCost.toFixed(2)}</div>
        </div>
      </div>

      <div className="form-row">
        <div className="form-group checkbox-group">
          <label>
            <input type="checkbox" name="customerApprovalRequired" checked={data.customerApprovalRequired} onChange={handleChange} />
            Customer Approval Required
          </label>
        </div>
        {data.customerApprovalRequired && (
          <div className="form-group">
            <label>Approval Status</label>
            <select name="approvalStatus" value={data.approvalStatus} onChange={handleChange}>
              <option value="">Select Status</option>
              <option value="PENDING">Pending</option>
              <option value="APPROVED">Approved</option>
              <option value="REJECTED">Rejected</option>
            </select>
          </div>
        )}
      </div>

      <div className="form-group checkbox-group">
        <label>
          <input type="checkbox" name="serviceAgreementConfirmed" checked={data.serviceAgreementConfirmed} onChange={handleChange} />
          Service Agreement Confirmed
        </label>
      </div>
    </div>
  );
}

export default ServiceDetailsStep;
