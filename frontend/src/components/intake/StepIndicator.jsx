const steps = ['Customer Details', 'Vehicle Details', 'Service Details', 'Company Details'];

function StepIndicator({ currentStep }) {
  return (
    <div className="step-indicator">
      {steps.map((label, index) => (
        <div
          key={label}
          className={`step ${index === currentStep ? 'active' : ''} ${index < currentStep ? 'completed' : ''}`}
        >
          <span className="step-number">
            {index < currentStep ? '\u2713' : index + 1}
          </span>
          <span className="step-label">{label}</span>
        </div>
      ))}
    </div>
  );
}

export default StepIndicator;
