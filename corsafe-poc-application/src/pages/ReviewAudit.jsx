import Checkbox from '@mui/material/Checkbox';
import { Button, Card, CardContent, FormControlLabel } from '@mui/material';
import React, { useState } from 'react';
import '../assets/styles/reviewAudits.css';
import axios from 'axios';
import { useSelector } from 'react-redux';

const ReviewAudit = () => {
  const token = useSelector((state) => state.auth.jwtToken);
  console.log("review token : ",token);
  
  
  const data = [
    {
      "text": "Please agree to framework"
    },
    {
      "text": "Agree to access your profile by auditor"
    },
    {
      "text": "Agree to ts&cs"
    }
  ];

  const [variables, setVariables] = useState([
    {
      "name": "agreeToFrameWork",
      "value": false 
    },
    {
      "name": "agreeToProfileAccess",
      "value": false
    },
    {
      "name": "agreeToTC",
      "value": false
    }
  ]);

 
  const handleCheckboxChange = (event, index) => {
    const updatedVariables = [...variables];
    updatedVariables[index].value = event.target.checked;
    setVariables(updatedVariables);
  };

  
  const handleSubmit = async () => {
    try {
      console.log("datataaaaaa: ",variables);
      
      const response = await axios.post('http://loaclhost:8086/auditRequest/completeTask1', { variables }, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      console.log("Data audit response: ", response);
    } catch (error) {
      console.error("Failed to post data:", error);
    }
  };

  return (
    <div className="card-container">
      <p className="heading">Please tick the checkboxes to confirm your preferences.</p>
      <Card elevation={6} style={{ backgroundColor: '#bdbdbd', width: '500px', maxHeight: '80vh' }}>
        <CardContent>
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'flex-start', marginLeft: '70px', marginRight: '50px' }}>
            {
              data.map((message, i) => (
                <FormControlLabel
                  key={i}
                  control={
                    <Checkbox
                      checked={variables[i].value}
                      onChange={(event) => handleCheckboxChange(event, i)}
                    />
                  }
                  label={message.text}
                />
              ))
            }
          </div>
        </CardContent>
      </Card>
      <Button
        style={{
          backgroundColor: '#1e88e5',
          color: 'white'
        }}
        onClick={handleSubmit}
      >
        Submit
      </Button>
    </div>
  );
}

export default ReviewAudit;
