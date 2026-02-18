function InternalDetailsStep({ data, serviceData, onChange }) {
  const handleChange = (e) => {
    const { name, value } = e.target;
    onChange('internal', name, value);
  };

  const laborCost = parseFloat(serviceData.laborCost) || 0;
  const partsCost = parseFloat(serviceData.partsCost) || 0;
  const totalCost = laborCost + partsCost;
  const vat = parseFloat(data.vatPercentage) || 0;
  const discount = parseFloat(data.discountPercentage) || 0;
  const vatAmount = (totalCost * vat) / 100;
  const discountAmount = (totalCost * discount) / 100;
  const finalAmount = Math.max(0, totalCost + vatAmount - discountAmount);

  return (
    <div className="intake-step">
      <h3 className="intake-section-title">Internal / Company Details</h3>

      <div className="form-row three-col">
        <div className="form-group">
          <label>Branch Location</label>
          <input type="text" name="branchLocation" value={data.branchLocation} onChange={handleChange} placeholder="Branch location" />
        </div>
        <div className="form-group">
          <label>Service Advisor</label>
          <input type="text" name="serviceAdvisor" value={data.serviceAdvisor} onChange={handleChange} placeholder="Service advisor name" />
        </div>
        <div className="form-group">
          <label>Custodian</label>
          <input type="text" name="custodian" value={data.custodian} onChange={handleChange} placeholder="Vehicle custodian" />
        </div>
      </div>

      <div className="form-row">
        <div className="form-group">
          <label>Work Order Number</label>
          <div className="calculated-field auto-gen">Auto-generated on submission</div>
        </div>
        <div className="form-group">
          <label>Intake Timestamp</label>
          <div className="calculated-field auto-gen">{new Date().toLocaleString()}</div>
        </div>
      </div>

      <div className="form-group">
        <label>Payment Method</label>
        <select name="paymentMethod" value={data.paymentMethod} onChange={handleChange}>
          <option value="">Select Payment Method</option>
          <option value="CASH">Cash</option>
          <option value="CREDIT_CARD">Credit Card</option>
          <option value="DEBIT_CARD">Debit Card</option>
          <option value="BANK_TRANSFER">Bank Transfer</option>
          <option value="CHEQUE">Cheque</option>
        </select>
      </div>

      <div className="form-row three-col">
        <div className="form-group">
          <label>VAT Percentage (%)</label>
          <input type="number" name="vatPercentage" value={data.vatPercentage} onChange={handleChange} min={0} max={100} step="0.01" placeholder="0.00" />
        </div>
        <div className="form-group">
          <label>Discount Percentage (%)</label>
          <input type="number" name="discountPercentage" value={data.discountPercentage} onChange={handleChange} min={0} max={100} step="0.01" placeholder="0.00" />
        </div>
        <div className="form-group">
          <label>Final Payable Amount (LKR)</label>
          <div className="calculated-field final-amount">{finalAmount.toFixed(2)}</div>
        </div>
      </div>

      <div className="cost-breakdown">
        <p>Total Cost: LKR {totalCost.toFixed(2)}</p>
        <p>+ VAT ({vat}%): LKR {vatAmount.toFixed(2)}</p>
        <p>- Discount ({discount}%): LKR {discountAmount.toFixed(2)}</p>
        <p className="final-line">= Final: LKR {finalAmount.toFixed(2)}</p>
      </div>

      <div className="form-group">
        <label>Internal Notes</label>
        <textarea name="internalNotes" value={data.internalNotes} onChange={handleChange} rows={4} maxLength={2000} placeholder="Internal notes (not visible to customer)..." />
      </div>
    </div>
  );
}

export default InternalDetailsStep;
