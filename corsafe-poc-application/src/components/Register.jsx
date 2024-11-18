import React, { useState } from 'react';
import { Box, Typography, Button, Card, CardContent, FormControlLabel, Checkbox, TextField } from '@mui/material';
import { useNavigate ,Link} from 'react-router-dom';
import Grid from '@mui/material/Grid2';


const Register = ({ userData, onUserChange }) => {
  const navigate = useNavigate();
  const [errors, setErrors] = useState({}); 
  const [touched, setTouched] = useState({}); 

  const navigation = () => {
    if (validateForm()) {
      navigate("/company");
    }
  };

  const validateForm = () => {
    const newErrors = {};
   
    if (!userData.firstName) {
      newErrors.firstName = 'First Name is required';
    }
    
    if (!userData.lastName) {
      newErrors.lastName = 'Last Name is required';
    }
    
    if (!userData.email) {
      newErrors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(userData.email)) {
      newErrors.email = 'Email is invalid';
    }
   
    if (!userData.password) {
      newErrors.password = 'Password is required';
    } else if (userData.password.length < 6) {
      newErrors.password = 'Password must be at least 6 characters';
    }
    
    if (!userData.phoneNumber) {
      newErrors.phoneNumber = 'Phone Number is required';
    } else if (!/^\d+$/.test(userData.phoneNumber)) {
      newErrors.phoneNumber = 'Phone Number must be numeric';
    }

    setErrors(newErrors); 
    return Object.keys(newErrors).length === 0; 
  };
  const handleLogin=()=>{
    navigate('/login');
  }

  return (
    <Box sx={{ flexGrow: 1, p: 2 }}>
      <Grid container spacing={4} justifyContent="center">
        <Grid item xs={12} sm={6} md={5}>
          <Card
            sx={{
              width: { xs: '100%', sm: '80%', md: '550px' },
              height: { xs: 'auto', md: '680px' },
              marginTop: { xs: '20px', md: '10px' },
              backgroundColor: '#2d1991',
              borderRadius: '25px',
              display: 'flex',
              flexDirection: 'column',
              padding: 0,
            }}
          >
            <CardContent sx={{ padding: '10px' }}>
              <Typography gutterBottom style={{ color: 'white', fontSize: { xs: 18, md: 14 }, marginTop: '40px', marginRight: '370px' }}>
                Welcome to
              </Typography>
              <img
                src="/coresafe_color.png"
                alt="Coresafe Logo"
                style={{ width: '180px', height: '50px', marginTop: '10px', marginRight: '280px' }}
              />
              <Typography style={{ color: 'white', fontSize: '25px', marginRight: '260px', marginTop: '10px' }}>
                <b>Australia's Best </b>
                <br />
                <span style={{ marginLeft: '25px' }}><b>Compliance For You</b></span>
              </Typography>
              <div>
                <img src="/truck_coresafe.jpg" style={{ width: '70%', marginTop: '80px', borderRadius: '20px' }} alt="Truck" />
              </div>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} sm={6} md={5}>
          <Typography style={{ color: '#2d1991', textAlign: 'end' }}><b>Help & Support</b></Typography>
          <Typography variant="h6" gutterBottom sx={{ textAlign: 'left', marginLeft: 0, marginTop: '40px', fontSize: '18px' }}>
            <b>Sign Up</b>
          </Typography>
          <Box component="form" sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            <TextField
              label="First Name"
              variant="outlined"
              fullWidth
              value={userData.firstName}
              onChange={(e) => {
                onUserChange('firstName', e.target.value);
                setTouched({ ...touched, firstName: true }); 
              }}
              required
              error={touched.firstName && !!errors.firstName} 
              helperText={touched.firstName && errors.firstName} 
            />
            <TextField
              label="Last Name"
              variant="outlined"
              fullWidth
              value={userData.lastName}
              onChange={(e) => {
                onUserChange('lastName', e.target.value);
                setTouched({ ...touched, lastName: true });
              }}
              required
              error={touched.lastName && !!errors.lastName}
              helperText={touched.lastName && errors.lastName}
            />
            <TextField
              label="Email"
              variant="outlined"
              type="email"
              fullWidth
              value={userData.email}
              onChange={(e) => {
                onUserChange('email', e.target.value);
                setTouched({ ...touched, email: true });
              }}
              required
              error={touched.email && !!errors.email}
              helperText={touched.email && errors.email}
            />
            <TextField
              label="Password"
              variant="outlined"
              type="password"
              fullWidth
              value={userData.password}
              onChange={(e) => {
                onUserChange('password', e.target.value);
                setTouched({ ...touched, password: true });
              }}
              required
              error={touched.password && !!errors.password}
              helperText={touched.password && errors.password}
            />
            <TextField
              label="Phone Number"
              variant="outlined"
              fullWidth
              value={userData.phoneNumber}
              onChange={(e) => {
                onUserChange('phoneNumber', e.target.value);
                setTouched({ ...touched, phoneNumber: true });
              }}
              required
              error={touched.phoneNumber && !!errors.phoneNumber}
              helperText={touched.phoneNumber && errors.phoneNumber}
            />

            <Box sx={{ display: 'flex', alignItems: 'center' }}>
              <FormControlLabel
                control={<Checkbox />}
                label={
                  <Typography variant="body2">
                    By continuing, you indicate that you have read and agreed to the{' '}
                    <span style={{ color: '#30f063' }}>Terms of Use</span>
                  </Typography>
                }
              />
            </Box>
            <Button variant="contained" onClick={navigation}>Continue</Button>

            <Typography variant="body2" align="center">
              Already have an account?{' '}
              <Link to='/login' style={{ color: '#141069', fontWeight: 'bold' }}>Log In</Link>
            </Typography>
            

          </Box>
        </Grid>
      </Grid>
    </Box>
  );
};

export default Register;
