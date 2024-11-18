import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';


export const fetchCartItems = createAsyncThunk(
  'cart/fetchCartItems',
  async ({userId,token }, { rejectWithValue }) => {
    try {
           console.log("userId and Token : ",userId,token);
           
      const response = await axios.get(`http://localhost:8086/cart/cartitems/${userId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
      console.log("cartItem response data : ",response.data.result);
      
      return response.data.result || []; 
      
    } catch (error) {
      console.log("cartItem error : ",error);
      
      return rejectWithValue(error.response.data);
    }

    
  }
);





const displayCartSlice = createSlice({
  name: 'carts',
  initialState: {
    cartId:null,
    items: [],
    loading: false,
    error: null,
  },
  reducers: {
  
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchCartItems.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchCartItems.fulfilled, (state, action) => {
        
        state.loading = false;
        state.items = action.payload || [];
        state.cartId = action.payload.cartId || null; 
      })
      .addCase(fetchCartItems.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload || 'Failed to fetch cart items';
      });
  }
});

export default displayCartSlice.reducer;


