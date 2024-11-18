import React, { useState } from 'react';
import { Box, Card, CardContent, Typography, Checkbox, TextField } from '@mui/material';
import Grid from '@mui/material/Grid2';

const SecurityQuestions = ({ securityData, onSecurityChange }) => {
  const [selectedQuestions, setSelectedQuestions] = useState([]);

  const handleCheckboxChange = (questionId) => {
    setSelectedQuestions((prev) => {
      const isSelected = prev.find((q) => q.questionId === questionId);

      if (isSelected) {
       
        return prev.filter((q) => q.questionId !== questionId);
      } else {
       
        return [...prev, { questionId, answer: '' }];
      }
    });
  };

  const handleInputChange = (questionId, value) => {
    setSelectedQuestions((prev) =>
      prev.map((q) =>
        q.questionId === questionId ? { ...q, answer: value } : q
      )
    );
  };

 
  React.useEffect(() => {
    onSecurityChange(selectedQuestions);
  }, [selectedQuestions, onSecurityChange]);

  return (
    <Box sx={{ flexGrow: 1, p: 2 }}>
      <Grid container spacing={4} justifyContent="center">
        <Grid item xs={12} sm={6} md={5}>
          <Card sx={{ width: 'auto' }}>
            <CardContent sx={{ padding: '10px' }}>
              <Typography gutterBottom sx={{ fontSize: { xs: 18, md: 14 } }}>
                <b>Security Questions</b>
              </Typography>
              {Array.isArray(securityData) && securityData.length > 0 ? (
                securityData.map((question) => (
                  <div key={question.id}>
                    <div style={{ display: 'flex', alignItems: 'center', marginBottom: '8px' }}>
                      <Checkbox 
                        checked={!!selectedQuestions.find((q) => q.questionId === question.id)}
                        onChange={() => handleCheckboxChange(question.id)}
                      />
                      <span style={{ marginRight: '8px' }}>{question.question}</span>
                    </div>
                    {selectedQuestions.find((q) => q.questionId === question.id) && (
                      <TextField
                        variant="outlined"
                        margin="normal"
                        placeholder="Your answer"
                        onChange={(e) => handleInputChange(question.id, e.target.value)}
                        fullWidth 
                      />
                    )}
                  </div>
                ))
              ) : (
                <Typography>No security questions available.</Typography>
              )}
            </CardContent>
          </Card>
        </Grid>
      </Grid>
    </Box>
  );
};

export default SecurityQuestions;
