function IntakeConfirmation({ response, onReset }) {
  return (
    <div className="confirmation-card">
      <div className="confirmation-icon">&#10003;</div>
      <h2>Service Intake Submitted Successfully</h2>

      <div className="work-order">
        {response.workOrderNumber}
      </div>

      <div className="confirmation-details">
        <div className="detail-row">
          <span className="detail-label">Customer:</span>
          <span className="detail-value">{response.customer?.fullName}</span>
        </div>
        <div className="detail-row">
          <span className="detail-label">Vehicle:</span>
          <span className="detail-value">{response.vehicle?.vehicleName} ({response.vehicle?.registrationNumber})</span>
        </div>
        <div className="detail-row">
          <span className="detail-label">Service Type:</span>
          <span className="detail-value">{response.service?.serviceType?.replace(/_/g, ' ')}</span>
        </div>
        <div className="detail-row">
          <span className="detail-label">Priority:</span>
          <span className="detail-value">{response.service?.servicePriority}</span>
        </div>
        <div className="detail-row">
          <span className="detail-label">Intake Time:</span>
          <span className="detail-value">{new Date(response.intakeTimestamp).toLocaleString()}</span>
        </div>
        {response.internal?.finalPayableAmount && (
          <div className="detail-row">
            <span className="detail-label">Final Amount:</span>
            <span className="detail-value">LKR {parseFloat(response.internal.finalPayableAmount).toFixed(2)}</span>
          </div>
        )}
      </div>

      <div className="confirmation-actions">
        <button className="btn-primary" onClick={onReset}>Create Another Intake</button>
      </div>
    </div>
  );
}

export default IntakeConfirmation;
