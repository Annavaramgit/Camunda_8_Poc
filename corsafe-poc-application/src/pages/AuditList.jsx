// src/App.js
import React, { useState } from 'react';
import AuditTable from './AuditTable';

const AuditList = () => {
  const [taskData, setTaskData] = useState([]);
  const [taskType, setTaskType] = useState('unasign');
    const[claimUnClaim,setClaimUnClaim] = useState("Claim");
    const[title,setTitle] = useState("Unassigned Task List");
  const data = [
    { name: 'John Doe', age: 28, email: 'mailto:john@example.com' },
    { name: 'Jane Smith', age: 34, email: 'mailto:jane@example.com' },
    { name: 'Sam Johnson', age: 45, email: 'mailto:sam@example.com' },
  ];

  const handleAsignUnAsign = ({ tasktype, claimUnClaim, title }) => {
    setTaskType(tasktype);
    setClaimUnClaim(claimUnClaim)
    setTitle(title)
   
  };

  return (
    <div className="App">
      <h1>{title}</h1>
      <div className='userTask-button'>
        <div className='unasign-btn'>
          <button 
            className={taskType === 'unasign' ? 'active' : ''} 
            onClick={() => handleAsignUnAsign({ tasktype: 'unasign', claimUnClaim: 'Claim', title: 'Unassigned Task List' })}
          >
            Unassigned
          </button>
        </div>
        <div className='asign-btn'>
          <button 
            className={taskType === 'asign' ? 'active' : ''} 
            onClick={() => handleAsignUnAsign({ tasktype: 'asign', claimUnClaim: 'Unclaim', title: 'Assigned Task List' })}
          >
            Assigned
          </button>
        </div>
      </div>
      <AuditTable data={data} claimUnClaim={claimUnClaim} />
    </div>
  );
};

export default AuditList;
