import { Button, Card, CardContent, TextField } from '@mui/material';
import axios from 'axios';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
// import { useDispatch } from 'react-redux';

const CustomerAddress = () => {
  const navigate = useNavigate();

  const token = useSelector((state) => state.auth.jwtToken);
  const userId = useSelector((state) => state.auth.userDetails?.userId);
  const cartId = useSelector((state) => state.totalprice?.cartId);
  const userdetails = useSelector((state)=>state.auth.userDetails);
  console.log(" startProcess userDetails : ", userdetails);
  

  const [formData, setFormData] = useState({
   
    state: '',
    pincode: '',
    city: '',
    houseNumber: '',
    street: ''
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };
  const savePayment = (payment_id) => {
    return axios.post(
      `http://localhost:8086/api/payments/save/${payment_id}`,
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    )
    .then((response) => {
      console.log("Payment saved successfully:", response.data);
    })
    .catch((error) => {
      console.error("Error saving payment:", error.response ? error.response.data : error.message);
    });
  };
  const updatePaymentStatus = (order_id, payment_id) => {
    return axios.post(
      'http://localhost:8086/api/payments/updatePaymentStatus',
      {
        razorPayOrderId: order_id,
        razorpayPaymentId: payment_id,
        status: 'Paid',
      },
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    ).then((response) => {
      console.log('Order status updated to Success.');
      
      return savePayment(payment_id);
    }).catch((error) => {
      console.error('Failed to update order status', error);
    });
  };

  const placeOrder = async (orderData) => {
    try {
      const response = await axios.post(
        'http://localhost:8086/cart/placeorder',
        orderData,
        {
          headers: {
            Authorization: `Bearer ${token}`  
          }
        }
      );
  
      if (response.data.statusCode === 200) {

        console.log("Order placed successfully: ", response.data);

        const options = {
          key: 'rzp_test_f0aOtbLVkyCYpW', 
          amount: response.data.result[0].amount,
          currency: "INR",
          name: 'Your Company Name',
          description: 'Order Payment',
          order_id: response.data.result[0].razorPayOrderId,
          handler: function (paymentResponse) {
            console.log("Payment response: ", paymentResponse);
          
            updatePaymentStatus(options.order_id, paymentResponse.razorpay_payment_id)
            .then(() => {
              navigate("/reviewAudit");
            });
          },
          prefill: {
              // name: user.firstName,
              // email: user.email,
              // contact: user.phoneNumber,
          },
          theme: {
              color: '#F37254',
          },
      };
      const rzp = new window.Razorpay(options);
      rzp.open();
      } else {
        console.log("Failed to place order: ", response.data);
      }
    } catch (error) {
      console.error("Error placing order: ", error.response ? error.response.data : error.message);
    }
  };
  
  const handlestartProcess = async() =>{
    try{
      const processResponse = axios.post('http://localhost:8086/auditRequest/startTask',
        {
          
            firstName:userdetails.firstName,
            lastName:userdetails.lastName,
            email:userdetails.email,
            phoneNumber:userdetails.phoneNumber,
             abn:userdetails.abn,
             companyName:userdetails.abn,
             companyAddress:userdetails.companyAddress,
             state:userdetails.state,
             postalCode:userdetails.postalCode
        
        },
       
        
        {
          headers:{
            Authorization: `Bearer ${token}`
          }
        }
      );
      console.log("startProcess : ",processResponse);
      
    }catch(error){
      console.log("failed to start : ",error);
      
    }
  }
  const handleSubmit = (e) => {
    e.preventDefault();
    //  dispatch(postCustomerAddress(formData)); 
    console.log("address details : ", formData);
    const orderData = {
      userId: userId,
      cartId: cartId,
      adress: formData.houseNumber + formData.street,
      city: formData.city,
      postalcode: formData.pincode,
      area: formData.street
    }
    console.log("order data request ",orderData);
    
    placeOrder(orderData);
    handlestartProcess();
   
  };

  return (
    <div>
      <Card sx={{ height: '100vh', backgroundColor: '#f5f5f5' }}>
        <CardContent>
          <p style={{ fontSize: '24px', margin: '20px' }}>Please fill in all details</p>
          <form onSubmit={handleSubmit}>
          
              <div style={{ display: 'flex', flexDirection: 'row', gap: 20 }}>
              <TextField
                label="State"
                name="state"
                value={formData.state}
                onChange={handleChange}
                fullWidth
                variant="standard"
              />
              <TextField
                label="Pincode"
                name="pincode"
                value={formData.pincode}
                onChange={handleChange}
                fullWidth
                variant="standard"
              />
            </div>
            <TextField
              label="City"
              name="city"
              value={formData.city}
              onChange={handleChange}
              fullWidth
              variant="standard"
            />
            <TextField
              label="House No, Building Name"
              name="houseNumber"
              value={formData.houseNumber}
              onChange={handleChange}
              fullWidth
              variant="standard"
            />
            <TextField
              label="Street No, Area"
              name="street"
              value={formData.street}
              onChange={handleChange}
              fullWidth
              variant="standard"
            />
            <Button type="submit" variant="contained" sx={{ marginTop: '20px' }}>Place Order</Button>
          </form>
        </CardContent>
      </Card>
    </div>
  );
};

export default CustomerAddress;