function VehicleDetailsStep({ data, onChange }) {
  const handleChange = (e) => {
    const { name, value } = e.target;
    onChange('vehicle', name, value);
  };

  const today = new Date().toISOString().split('T')[0];
  const currentYear = new Date().getFullYear();

  return (
    <div className="intake-step">
      <h3 className="intake-section-title">Vehicle Details</h3>

      <div className="form-row">
        <div className="form-group">
          <label>Vehicle Name *</label>
          <input type="text" name="vehicleName" value={data.vehicleName} onChange={handleChange} required placeholder="e.g., Toyota Corolla 2020" />
        </div>
        <div className="form-group">
          <label>Registration Number *</label>
          <input type="text" name="registrationNumber" value={data.registrationNumber} onChange={handleChange} required placeholder="e.g., ABC-1234" />
        </div>
      </div>

      <div className="form-row">
        <div className="form-group">
          <label>Chassis Number *</label>
          <input type="text" name="chassisNumber" value={data.chassisNumber} onChange={handleChange} required placeholder="Chassis/VIN number" />
        </div>
        <div className="form-group">
          <label>Engine Number</label>
          <input type="text" name="engineNumber" value={data.engineNumber} onChange={handleChange} placeholder="Engine number" />
        </div>
      </div>

      <div className="form-row three-col">
        <div className="form-group">
          <label>Brand *</label>
          <input type="text" name="brand" value={data.brand} onChange={handleChange} required placeholder="e.g., Toyota" />
        </div>
        <div className="form-group">
          <label>Model *</label>
          <input type="text" name="model" value={data.model} onChange={handleChange} required placeholder="e.g., Corolla" />
        </div>
        <div className="form-group">
          <label>Manufacturing Year *</label>
          <input type="number" name="manufacturingYear" value={data.manufacturingYear} onChange={handleChange} required min={1900} max={currentYear} placeholder="e.g., 2020" />
        </div>
      </div>

      <div className="form-row three-col">
        <div className="form-group">
          <label>Vehicle Type *</label>
          <select name="vehicleType" value={data.vehicleType} onChange={handleChange} required>
            <option value="">Select Type</option>
            <option value="SEDAN">Sedan</option>
            <option value="SUV">SUV</option>
            <option value="HATCHBACK">Hatchback</option>
            <option value="TRUCK">Truck</option>
            <option value="VAN">Van</option>
            <option value="COUPE">Coupe</option>
            <option value="CONVERTIBLE">Convertible</option>
            <option value="WAGON">Wagon</option>
            <option value="MOTORCYCLE">Motorcycle</option>
            <option value="BUS">Bus</option>
            <option value="OTHER">Other</option>
          </select>
        </div>
        <div className="form-group">
          <label>Fuel Type *</label>
          <select name="fuelType" value={data.fuelType} onChange={handleChange} required>
            <option value="">Select Fuel</option>
            <option value="PETROL">Petrol</option>
            <option value="DIESEL">Diesel</option>
            <option value="HYBRID">Hybrid</option>
            <option value="ELECTRIC">Electric</option>
            <option value="LPG">LPG</option>
            <option value="CNG">CNG</option>
          </select>
        </div>
        <div className="form-group">
          <label>Transmission *</label>
          <select name="transmissionType" value={data.transmissionType} onChange={handleChange} required>
            <option value="">Select Transmission</option>
            <option value="MANUAL">Manual</option>
            <option value="AUTOMATIC">Automatic</option>
            <option value="CVT">CVT</option>
            <option value="SEMI_AUTOMATIC">Semi-Automatic</option>
          </select>
        </div>
      </div>

      <div className="form-row">
        <div className="form-group">
          <label>Mileage (km) *</label>
          <input type="number" name="mileage" value={data.mileage} onChange={handleChange} required min={1} step="0.1" placeholder="Current mileage" />
        </div>
        <div className="form-group">
          <label>Color</label>
          <input type="text" name="color" value={data.color} onChange={handleChange} placeholder="Vehicle color" />
        </div>
      </div>

      <div className="form-row">
        <div className="form-group">
          <label>Insurance Provider</label>
          <input type="text" name="insuranceProvider" value={data.insuranceProvider} onChange={handleChange} placeholder="Insurance company name" />
        </div>
        <div className="form-group">
          <label>Insurance Expiry Date</label>
          <input type="date" name="insuranceExpiryDate" value={data.insuranceExpiryDate} onChange={handleChange} min={today} />
        </div>
      </div>

      <div className="form-row">
        <div className="form-group">
          <label>Warranty Status</label>
          <select name="warrantyStatus" value={data.warrantyStatus} onChange={handleChange}>
            <option value="">Select Status</option>
            <option value="ACTIVE">Active</option>
            <option value="EXPIRED">Expired</option>
            <option value="NOT_APPLICABLE">Not Applicable</option>
          </select>
        </div>
        <div className="form-group">
          <label>Last Service Date</label>
          <input type="date" name="lastServiceDate" value={data.lastServiceDate} onChange={handleChange} max={today} />
        </div>
      </div>
    </div>
  );
}

export default VehicleDetailsStep;
