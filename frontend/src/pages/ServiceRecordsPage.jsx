import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getAllIntakes, deleteServiceRecord } from '../services/intakeService';

function ServiceRecordsPage() {
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState('');
  const [filterPriority, setFilterPriority] = useState('');
  const navigate = useNavigate();

  const fetchRecords = async () => {
    setLoading(true);
    setError(null);
    try {
      const res = await getAllIntakes();
      setRecords(res.data);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to load service records.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchRecords();
  }, []);

  const handleDelete = async (id, workOrder) => {
    if (!window.confirm(`Are you sure you want to delete ${workOrder}?`)) return;
    try {
      await deleteServiceRecord(id);
      setRecords(prev => prev.filter(r => r.serviceRecordId !== id));
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to delete record.');
    }
  };

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

  const filtered = records.filter(r => {
    const term = searchTerm.toLowerCase();
    const matchesSearch = !term ||
      r.workOrderNumber?.toLowerCase().includes(term) ||
      r.customer?.fullName?.toLowerCase().includes(term) ||
      r.customer?.nicNumber?.toLowerCase().includes(term) ||
      r.vehicle?.registrationNumber?.toLowerCase().includes(term) ||
      r.vehicle?.vehicleName?.toLowerCase().includes(term);

    const matchesPriority = !filterPriority || r.service?.servicePriority === filterPriority;

    return matchesSearch && matchesPriority;
  });

  if (loading) {
    return <div className="loading">Loading service records...</div>;
  }

  return (
    <div className="records-page">
      <div className="records-header">
        <div>
          <h2>Service Records</h2>
          <p className="records-subtitle">{records.length} total record{records.length !== 1 ? 's' : ''}</p>
        </div>
        <button className="btn-primary" onClick={() => navigate('/intake')}>+ New Intake</button>
      </div>

      {error && <div className="error-alert">{error}</div>}

      <div className="records-filters">
        <div className="search-box">
          <input
            type="text"
            placeholder="Search by work order, customer, NIC, vehicle..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>
        <select value={filterPriority} onChange={(e) => setFilterPriority(e.target.value)}>
          <option value="">All Priorities</option>
          <option value="LOW">Low</option>
          <option value="NORMAL">Normal</option>
          <option value="HIGH">High</option>
          <option value="URGENT">Urgent</option>
        </select>
      </div>

      {filtered.length === 0 ? (
        <div className="records-empty">
          <p>{searchTerm || filterPriority ? 'No records match your filters.' : 'No service records yet.'}</p>
          {!searchTerm && !filterPriority && (
            <button className="btn-primary" onClick={() => navigate('/intake')}>Create First Intake</button>
          )}
        </div>
      ) : (
        <div className="records-table-wrapper">
          <table className="records-table">
            <thead>
              <tr>
                <th>Work Order</th>
                <th>Customer</th>
                <th>Vehicle</th>
                <th>Service Type</th>
                <th>Priority</th>
                <th>Intake Date</th>
                <th>Total Cost</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filtered.map((record) => (
                <tr key={record.serviceRecordId}>
                  <td>
                    <span className="wo-number">{record.workOrderNumber}</span>
                  </td>
                  <td>
                    <div className="cell-main">{record.customer?.fullName || '-'}</div>
                    <div className="cell-sub">{record.customer?.nicNumber}</div>
                  </td>
                  <td>
                    <div className="cell-main">{record.vehicle?.vehicleName || '-'}</div>
                    <div className="cell-sub">{record.vehicle?.registrationNumber}</div>
                  </td>
                  <td>{record.service?.serviceType?.replace(/_/g, ' ') || '-'}</td>
                  <td>
                    <span className={`priority-badge ${getPriorityClass(record.service?.servicePriority)}`}>
                      {record.service?.servicePriority || '-'}
                    </span>
                  </td>
                  <td>{formatDateTime(record.intakeTimestamp)}</td>
                  <td className="cell-amount">{formatCurrency(record.internal?.finalPayableAmount || record.service?.totalCost)}</td>
                  <td>
                    <div className="table-actions">
                      <button className="btn-view" onClick={() => navigate(`/records/${record.serviceRecordId}`)}>View</button>
                      <button className="btn-delete" onClick={() => handleDelete(record.serviceRecordId, record.workOrderNumber)}>Delete</button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
}

export default ServiceRecordsPage;
