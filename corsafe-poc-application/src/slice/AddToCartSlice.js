import axios from 'axios';
import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';

export const postAddtocart = createAsyncThunk(
   'cart/postAddtocart',
    async ({ userId, productId,token,stockQuantity}, { rejectWithValue }) => {
        try {
            console.log("Sending to API:", {userId,productId,stockQuantity,token}); 
          
            const response = await axios.post('http://localhost:8086/cart/cartadd',
                {
                    userId,
                    productId,
                    quantity:stockQuantity
                },
                {
                    headers: {
                        'Content-Type': 'application/json',
                       'Authorization': `Bearer ${token}`,
                    }
                    
                }
            );
            console.log("API Response:", response.data); 
            return response.data; 
        } catch (error) {
            console.error("API Error:", error);
            return rejectWithValue(error.response.data);
        }
    }
);


const initialState = {
    addTocart: null,
    cartId:null,
    error: null,
    status: 'idle' 
};


const cartSlice = createSlice({
    name: 'cart',
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(postAddtocart.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(postAddtocart.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.addTocart = action.payload;
                console.log("Response result:", action.payload.result[0]?.cartId);
                state.cartId = action.payload.result[0]?.cartId || null; 
                state.error = null;
            })
            .addCase(postAddtocart.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.payload;
            });
    },
});

export default cartSlice.reducer;

