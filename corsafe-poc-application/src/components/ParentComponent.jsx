import React, { useState, useEffect, useRef } from 'react';
import { Box, Button, Snackbar, Alert } from '@mui/material';
import Register from './Register';
import Company from './Company';
import { useDispatch, useSelector } from 'react-redux';
import { registerUser } from '../actions/registerActions';
import Grid from '@mui/material/Grid2';
import { useLocation, useNavigate } from 'react-router-dom';
import SecurityQuestions from './securityQuestions';

const RegistrationForm = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();


  const {data, error } = useSelector((state) => state.register);

  const location = useLocation();
  const validateCompanyRef = useRef(null);

  const [formData, setFormData] = useState({
    user: {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      phoneNumber: '',
    },
    company: {
      abn: '',
      companyName: '',
      companyAddress: '',
      state: '',
      postalCode: '',
    },
    securityQuestionList: [] 
  });

  const [securityQuestions, setSecurityQuestions] = useState([]);
  const [openSnackbar, setOpenSnackbar] = useState(false); 

  useEffect(() => {
    const fetchSecurityQuestions = async () => {
      try {
        const response = await fetch('http://localhost:8086/getAllSecurityQuestion');
        const data = await response.json();
        if (data.status === 'SUCCESS' && Array.isArray(data.result)) {
          setSecurityQuestions(data.result);
        } else {
          console.error('Unexpected response structure:', data);
        }
      } catch (error) {
        console.error('Error fetching security questions:', error);
      }
    };
    fetchSecurityQuestions();
  }, [0]);


  const handleChange = (section, field, value) => {
    setFormData((prevState) => ({
      ...prevState,
      [section]: {
        ...prevState[section],
        [field]: value,
      },
    }));
  };

  const handleSecurityChange = (questions) => {
    setFormData((prevState) => ({
      ...prevState,
      securityQuestionList: questions,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (location.pathname === '/security') {
      const combinedData = {
        ...formData.user,
        ...formData.company,
        securityQuestionList: formData.securityQuestionList,
      };
      dispatch(registerUser(combinedData));
      navigate("/login")
    }
  };

 
  useEffect(() => {
    if (data) {
      setOpenSnackbar(true); 
    }
  }, [data]);

  const handleCloseSnackbar = () => {
    setOpenSnackbar(false);
  };

  return (
    <Box sx={{ flexGrow: 1, p: 2 }}>
      <Grid container spacing={4} justifyContent="center">
        <Grid item xs={12} sm={6}>
          {location.pathname === '/' && (
            <Register
              userData={formData.user}
              onUserChange={(field, value) => handleChange('user', field, value)}
            />
          )}
        </Grid>
        <Grid item xs={12} sm={6}>
          {location.pathname === '/security' && (
            <>
              <SecurityQuestions
                securityData={securityQuestions}
                onSecurityChange={handleSecurityChange}
              />
              <Button variant="contained" onClick={handleSubmit}>
                Submit All
              </Button>
            </>
          )}
        </Grid>
        {location.pathname === '/company' && (
          <Grid item xs={12} sm={6}>
            <Company
              companyData={formData.company}
              onCompanyChange={(field, value) => handleChange('company', field, value)}
              onValidate={validateCompanyRef}
            />
          </Grid>
        )}
      </Grid>

     
      <Snackbar
        open={openSnackbar}
        autoHideDuration={6000}
        onClose={handleCloseSnackbar}
        anchorOrigin={{ vertical: 'right', horizontal: 'right' }}
      >
        <Alert onClose={handleCloseSnackbar} severity="success" sx={{ width: '100%' }}>
          Registered successfully!
        </Alert>
      </Snackbar>

      {error && (
        <Snackbar
          open={Boolean(error)}
          autoHideDuration={6000}
          onClose={handleCloseSnackbar}
          anchorOrigin={{ vertical: 'right', horizontal: 'right' }}
        >
          <Alert onClose={handleCloseSnackbar} severity="error" sx={{ width: '100%' }}>
            {error}
          </Alert>
        </Snackbar>
      )}
    </Box>
  );
};

export default RegistrationForm;
