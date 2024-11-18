import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

export const fetchTotalPrice = createAsyncThunk(
    'cart/fetchTotalPrice',
      async ({ userId ,token}, { rejectWithValue }) => {
        try {
          const res = await axios.get(`http://localhost:8086/cart/getTotalPrice/${userId}`,{
           headers:{
            'Authorization':`Bearer ${token}`
           }
          });
          console.log("TotalPrice data: ", res.data.result[0]);
          return res.data.result; 
        } catch (error) {
          console.log("TotalPrice error: ", error);
          return rejectWithValue(error.response?.data || 'Failed to fetch total price');
        }
      }
  )
  
  
  export const displayTotalpriceSlice = createSlice({
    name: 'priceCart',
    initialState: {
      totalPrice: null,
      cartId:null,
      loading: false,
      error: null,
    },
    reducers: {},
    extraReducers: (builder) => {
      builder
        .addCase(fetchTotalPrice.pending, (state) => {
          state.loading = true;
          state.error = null;
        })
        .addCase(fetchTotalPrice.fulfilled, (state, action) => {
          console.log("chechk2 : ",action.payload[0].totalPrice);
          console.log("hdfhshfdhfg: ",action.payload[0].cartId);
          
          state.loading = false;
          state.totalPrice = action.payload[0].totalPrice;
          state.cartId=action.payload[0].cartId;
                  
          })
        .addCase(fetchTotalPrice.rejected, (state, action) => {
          state.loading = false;
          state.error = action.payload || 'Failed to fetch total price';
        });
    }
  });
  
  
  export default displayTotalpriceSlice.reducer;