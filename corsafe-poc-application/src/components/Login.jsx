import { Box, Typography, Card, CardContent, TextField, Button } from '@mui/material';
import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import './googlelogin.css';
import Grid from '@mui/material/Grid2'; 
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import { loginUser } from '../reducers/loginReducer';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { loading, error } = useSelector((state) => state.auth);


  const handleSubmit = async (e) => {
    e.preventDefault();
    const resultAction = await dispatch(loginUser({ email, password }));
    if (loginUser.fulfilled.match(resultAction)) {

        toast.success('Login successful!');
        navigate('/allProducts/addTocart'); 
      } else if (loginUser.rejected.match(resultAction)) {
        toast.error('Invalid credentials. Please try again.');
      }
};

    
  

  const handleGoogleLogin = async () => {
    window.location.href = 'http://localhost:8086/user';
  };
  const handleRegister=()=>{
    navigate("/");
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
              <Typography
                gutterBottom
                sx={{ color: 'white', fontSize: { xs: 18, md: 14 }, marginTop: '40px', marginRight: '370px' }}
              >
                Welcome to
              </Typography>
              <img
                src="/coresafe_color.png"
                alt="Coresafe Logo"
                style={{ width: '180px', height: '50px', marginTop: '10px', marginRight: '280px' }}
              />
              <Typography
                sx={{ color: 'white', fontSize: '25px', marginRight: '260px', marginTop: '10px' }}
              >
                <b>Australia's Best </b>
                <br />
                <span style={{ marginLeft: '25px' }}><b>Compliance For You</b></span>
              </Typography>
              <div>
                <img
                  src="/truck_coresafe.jpg"
                  style={{ width: '70%', marginTop: '80px', borderRadius: '20px' }}
                  alt="Truck"
                />
              </div>
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} sm={6} md={5}>
          <Typography sx={{ color: '#2d1991', textAlign: 'end' }}>
            <b>Help & Support</b>
          </Typography>
          <Typography
            variant="h6"
            gutterBottom
            sx={{ textAlign: 'left', marginLeft: 0, marginTop: '40px', fontSize: '18px' }}
          >
            <b>Sign Up</b>
          </Typography>
          
          <Box component="form" onSubmit={handleSubmit} sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
            <TextField
              label="Email"
              variant="outlined"
              type="email"
              fullWidth
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
            <TextField
              label="Password"
              variant="outlined"
              type="password"
              fullWidth
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />

            <Button
              type="submit"
              variant="contained"
              sx={{ backgroundColor: '#2d1991' }}
              disabled={loading}
            >
              {loading ? 'Logging in...' : 'Login'}
            </Button>


            {/* Google Login Button with custom CSS */}
              <Button
  
                className="login-with-google-btn"
                onClick={handleGoogleLogin}
                sx={{ marginTop: 2 }} // Add some margin for spacing
              >
                Login with Google
              </Button>
              


            {error && (
              <Typography color="error" align="center">
                {error.message || 'Login failed. Please try again.'}
              </Typography>
            )}

            <Typography variant="body2" align="center">
              Register account here?{' '}
              <span style={{ color: '#141069', fontWeight: 'bold', cursor:"pointer"}} onClick={handleRegister} >Register</span>
            </Typography>


          </Box>
        </Grid>
      </Grid>
    </Box>
  );
};

export default Login;
