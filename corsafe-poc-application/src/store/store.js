import { legacy_createStore as createStore} from 'redux'
import { applyMiddleware } from 'redux';
import {thunk} from 'redux-thunk';
import { combineReducers } from 'redux';
import registerReducer from '../reducers/registerReducer';
import loginReducer from '../reducers/loginReducer';
import productReducer from '../slice/ProductSlice'

import { counterReducer } from '../slice/QuantitySlice';
import cartItemReducer from '../slice/DisplayCartItemsSlice';
import addcartReducer from '../slice/AddToCartSlice';
import TotalPriceReducer from '../slice/TotalPriceSlice';
const rootReducer = combineReducers({
  register: registerReducer,
  auth: loginReducer,
  products:productReducer,
  carts:cartItemReducer,
  addcart:addcartReducer,
  totalprice:TotalPriceReducer,
  counter:counterReducer
  
});

const store = createStore(rootReducer, applyMiddleware(thunk));

export default store;