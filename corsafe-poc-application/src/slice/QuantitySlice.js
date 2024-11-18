import axios from 'axios';
import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';

export const changeQuantity = createAsyncThunk(
   'cart/changeQuantity',
    async ({ userId, productId, token }, { rejectWithValue }) => {
        try {
            console.log("Sending to API:", { userId, productId, token }); 
            const response = await axios.post(
                'http://localhost:8086/cart/addQuantity',
                { userId, productId },
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


export const reduceQuantity = createAsyncThunk(
    'cart/reduceQuantity',
    async ({ userId, productId, token }, { rejectWithValue }) => {
        try {
            console.log("Sending to API:", { userId, productId, token }); 
            const response = await axios.post(
                'http://localhost:8086/cart/removeQuantity',
                { userId, productId },
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
    addQuantity: null,
    decreaseQuantity: null,
    error: null,
    status: 'idle'
};


const cartSlice = createSlice({
    name: 'cart',
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
         
            .addCase(changeQuantity.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(changeQuantity.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.addQuantity = action.payload;
                state.error = null;
            })
            .addCase(changeQuantity.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.payload;
            })
        
            .addCase(reduceQuantity.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(reduceQuantity.fulfilled, (state, action) => {
                state.status = 'succeeded';
                state.decreaseQuantity = action.payload;
                state.error = null;
            })
            .addCase(reduceQuantity.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.payload;
            });
    },
});

export const cartReducer = cartSlice.reducer;


const initialCountState = {
    count: 0
};

export const counterSlice = createSlice({
    name: 'counter',
    initialState: initialCountState,
    reducers: {
        increment: (state, action) => {
            state.count += action.payload;
        },
        decrement: (state, action) => {
            state.count -= action.payload;
        }
       
    },
});

export const { increment, decrement } = counterSlice.actions;
export const counterReducer = counterSlice.reducer;
