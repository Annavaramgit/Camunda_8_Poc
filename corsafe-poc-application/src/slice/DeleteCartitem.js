import axios from 'axios';
import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
export const deleteCartItem = createAsyncThunk(
    'cart/deleteCartItem',
    async ({ userId, productId,token,quantity}, { rejectWithValue }) => {
        try {
            console.log("Sending to API:", {userId,productId,quantity,token}); 
          
            const response = await axios.post('http://localhost:8086/cart/removeItem',
                {
                    userId,
                    productId,
                    quantity
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
)

const initialState1 = {
    items: [],
    cartId: null,
    loading: false,
    error: null,
    status: 'idle',
};


export const cartSlice2 = createSlice({
    name: 'cart',
    initialState1,
    reducers: {
        
        deleteFromCart: (state, action) => {
            const itemId = action.payload.id;
            state.items = state.items.filter(item => item.cartItemId !== itemId);
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(deleteCartItem.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(deleteCartItem.fulfilled, (state, action) => {
                state.status = 'succeeded';
                const { cartId, items } = action.payload; 
                state.cartId = cartId;
                state.items = items; 
                state.error = null;
                
            })
            .addCase(deleteCartItem.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.payload;
            });
    },
});


