import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

export const loginUser = createAsyncThunk(
  'auth/loginUser',
  async (loginData, { rejectWithValue }) => {
    try { 
      const response = await axios.post('http://localhost:8086/login', loginData);
      const { jwtToken, refreshToken, userDetails } = response.data.result[0];
      const { userId, firstName, lastName, email, phoneNumber, abn, companyName, companyAddress, state, postalCode } = userDetails;
      console.log("login response: ", response);      
      localStorage.setItem('jwtToken', jwtToken);
      localStorage.setItem('refreshToken', refreshToken);
      localStorage.setItem('userDetails', JSON.stringify({ userId, firstName, lastName, email, phoneNumber, abn, companyName, companyAddress, state, postalCode }));
      
      return { jwtToken, refreshToken, userDetails }; 
    } catch (error) {
      return rejectWithValue(error.response.data);
    }
  }
);

const loginSlice = createSlice({
  name: 'auth',
  initialState: {
    jwtToken: localStorage.getItem('jwtToken') || null,
    refreshToken: localStorage.getItem('refreshToken') || null,
    userDetails: JSON.parse(localStorage.getItem('userDetails')) || null, 
    loading: false,
    error: null,
  },
  reducers: {
    logout(state) {
      state.jwtToken = null;
      state.refreshToken = null;
      state.userDetails = null;
      localStorage.removeItem('jwtToken');
      localStorage.removeItem('refreshToken');
      localStorage.removeItem('userDetails');
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(loginUser.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(loginUser.fulfilled, (state, action) => {
        state.loading = false;
        state.jwtToken = action.payload.jwtToken;
        state.refreshToken = action.payload.refreshToken;
        state.userDetails = action.payload.userDetails;
      })
      .addCase(loginUser.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});


export const { logout } = loginSlice.actions;
export default loginSlice.reducer;
