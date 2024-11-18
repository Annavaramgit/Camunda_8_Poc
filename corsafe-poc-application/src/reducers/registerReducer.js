import {
    REGISTER_USER_REQUEST,
    REGISTER_USER_SUCCESS,
    REGISTER_USER_FAILURE,
  } from '../actions/actionTypes';
  
  const initialState = {
    loading: false,
    data: null,
    error: null,
  };
  
  const registerReducer = (state = initialState, action) => {
    switch (action.type) {
      case REGISTER_USER_REQUEST:
        return { ...state, loading: true, error: null };
      case REGISTER_USER_SUCCESS:
        return { ...state, loading: false, data: action.payload };
      case REGISTER_USER_FAILURE:
        return { ...state, loading: false, error: action.payload };
      default:
        return state;
    }
  };
  
  export default registerReducer;
  