// src/AuditTable.js
import React from 'react';
import './table.css';

const AuditTable = ({ data, claimUnClaim }) => {
  const handleButtonClick = (name) => {
    alert(`Button clicked for ${name}`);
  };

  const handleEyeClick = (name) => {
    alert(`Eye icon clicked for ${name}`);
  };

  return (
    <div className="table-container">
      <table>
        <thead>
          <tr>
            {Object.keys(data[0]).map((key) => (
              <th key={key}>{key.charAt(0).toUpperCase() + key.slice(1)}</th>
            ))}
            <th>Action</th>
            {claimUnClaim === 'Unclaim' && (
            <th>View</th>
            )}
          </tr>
        </thead>
        <tbody>
          {data.map((item, index) => (
            <tr key={index}>
              {Object.values(item).map((value, i) => (
                <td key={i}>{value}</td>
              ))}
              <td>
                <button className='cliam-btn' onClick={() => handleButtonClick(item.name)}>
                  {claimUnClaim}
                </button>
                </td>
                
                {claimUnClaim === 'Unclaim' && (
                    <td>
                    <button className='eye-btn' onClick={() => handleEyeClick(item.name)}>
                    👁️ {/ Eye icon /}
                  </button>
                    </td>
                 
                )}
             
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AuditTable;
