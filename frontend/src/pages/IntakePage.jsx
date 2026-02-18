import IntakeFormWizard from '../components/intake/IntakeFormWizard';

function IntakePage() {
  return (
    <div className="intake-page">
      <h2>Service Intake Form</h2>
      <p className="intake-subtitle">Complete all sections to create a new service record</p>
      <IntakeFormWizard />
    </div>
  );
}

export default IntakePage;
