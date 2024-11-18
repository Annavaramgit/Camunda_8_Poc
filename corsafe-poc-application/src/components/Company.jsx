import {React,useState} from 'react';
import { Box, Typography, Card, CardContent, TextField, Button } from '@mui/material';
import Grid from '@mui/material/Grid2';
import { useNavigate } from 'react-router-dom';

const Company = ({companyData,onCompanyChange,onValidate}) => {
    const navigate=useNavigate();
    const [errors, setErrors] = useState({}); 
    const [touched, setTouched] = useState({}); 
    const navigateSecurity=()=>
    {
        navigate("/security")
    }
  const validateForm=()=>
  {
     const newErrors={};
     if(!companyData.abn)
     {
        newErrors.abn="Abn is required";
     }
     if(!companyData.companyName)
     {
        newErrors.companyName="CompanyName is required";
     }
     if(!companyData.companyAddress)
     {
        newErrors.companyAddress="Company Address is required";
     }
     if(!companyData.state)
     {
        newErrors.state="State is required";
     }
     if(!companyData.postalCode)
     {
        newErrors.postalCode="Postal code is required";
     }
     setErrors(newErrors); 
    return Object.keys(newErrors).length === 0; 

  }
  onValidate.current = validateForm;
    return (
        <Box sx={{ flexGrow: 1, p: 2 }}>
            <Grid container spacing={4} justifyContent="center">
                <Grid item xs={12} sm={6} md={5}>
                    <Card
                        sx={{
                            width: { xs: '100%', sm: '80%', md: '550px' },
                            height: { xs: 'auto', md: '640px' },
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
                                style={{ color: 'white', fontSize: { xs: 18, md: 14 }, marginTop: '40px', marginRight: '370px' }}
                            >
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
                                <img src="/truck_coresafe.jpg" style={{ width: '70%', marginTop: '70px', borderRadius: '20px' }} alt="Truck" />
                            </div>
                        </CardContent>
                    </Card>
                </Grid>

                <Grid item xs={12} sm={6} md={5}>
                    
                    <Typography variant="h6" gutterBottom sx={{ textAlign: 'left', marginLeft: 0, marginTop: '40px', fontSize: '18px' }}>
                        <b>Company Information</b>
                    </Typography>
                    <Box component="form"  sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <Typography sx={{ marginBottom: '5px', marginTop: '10px' }} align="left">
                                    <b>ABN</b>
                                </Typography>
                                <TextField
                                    id="abn"
                                    variant="outlined"
                                    placeholder="Enter ABN number"
                                    fullWidth
                                    value={companyData.abn}
                                    onChange={(e) => {
                                        onCompanyChange('abn', e.target.value);
                                        setTouched({ ...touched, abn: true }); 
                                      }}
                                     
                                    required
                                    error={touched.abn && !!errors.abn} 
                                    helperText={touched.abn && errors.abn}
                                />
                            </Grid>
                        </Grid>

                        <Box>
                            <Typography sx={{ marginBottom: '5px' }} align="left">
                                <b>Company Name</b>
                            </Typography>
                            <TextField
                                id="companyName"
                                variant="outlined"
                                placeholder="Enter Company Name"
                                fullWidth
                                value={companyData.companyName}
                                onChange={(e) => {onCompanyChange('companyName', e.target.value);
                                    setTouched({...touched,companyName:true})}}
                                required
                                error={touched.companyName && !!errors.companyName} 
                                helperText={touched.companyName && errors.companyName}
                            />
                        </Box>

                        <Box>
                            <Typography sx={{ marginBottom: '5px' }} align="left">
                                <b>Company Address</b>
                            </Typography>
                            <TextField
                                id="companyAddress"
                                variant="outlined"
                                placeholder="Enter Company Address"
                                fullWidth
                                value={companyData.companyAddress}
                                onChange={(e) => {onCompanyChange('companyAddress', e.target.value);
                                    setTouched({...touched,companyAddress:true})}
                                }
                                required
                                error={touched.companyAddress && !!errors.companyAddress} 
                                helperText={touched.companyAddress && errors.companyAddress}
                            />
                        </Box>

                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6}>
                                <Typography sx={{ marginBottom: '5px', marginTop: '10px' }} align="left">
                                    <b>State</b>
                                </Typography>
                                <TextField
                                    id="state"
                                    variant="outlined"
                                    placeholder="Enter State"
                                    fullWidth
                                    value={companyData.state}
                                    onChange={(e) =>{ onCompanyChange('state', e.target.value)
                                        setTouched({...touched,state:true})
                                    }}
                                    required
                                    error={touched.state && !!errors.state} 
                                    helperText={touched.state && errors.state}
                                />
                            </Grid>

                            <Grid item xs={12} sm={6}>
                                <Typography sx={{ marginBottom: '5px', marginTop: '10px' }} align="left">
                                    <b>Postal Code</b>
                                </Typography>
                                <TextField
                                    id="postalCode"
                                    variant="outlined"
                                    placeholder="Postal Code"
                                    fullWidth
                                    value={companyData.postalCode}
                                    onChange={(e) =>{ onCompanyChange('postalCode', e.target.value);
                                    setTouched({...touched,postalCode:true})}}
                                    required
                                    error={touched.postalCode && !!errors.postalCode} 
                                    helperText={touched.postalCode && errors.postalCode}
                                />
                            </Grid>
                        </Grid>
                     <Button variant="contained" onClick={navigateSecurity} style={{marginTop:'40px'}}>Continue</Button>
                       

                        
                    </Box>
                </Grid>
            </Grid>
        </Box>
    );
};

export default Company;
