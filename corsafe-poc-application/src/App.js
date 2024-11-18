import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import RegistrationForm from './components/ParentComponent';
import Login from './components/Login';
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from 'react-toastify';
import AuthCallback from './components/AuthCallback';
import GetProducts from './pages/GetProducts';
import AddToCart from './pages/AddToCart';
import CustomerAddress from './pages/CustomerAddress';
import Payment from './pages/Payment';
import ReviewAudit from './pages/ReviewAudit';


function App() {
  return (
    <div className="App">


      <BrowserRouter>
        <ToastContainer />
        <Routes>
          <Route path="/" element={<RegistrationForm />} />
          <Route path="/company" element={<RegistrationForm />} />
          <Route path="/security" element={<RegistrationForm />} />
          <Route path="/login" element={<Login />} />
          <Route path="/auth/callback" element={<AuthCallback />} />
   
          <Route path="/allProducts" element={<GetProducts />}>
            <Route path="addTocart" element={<AddToCart />} />
            <Route path="address" element={<CustomerAddress />} />
            <Route path="payment" element={<Payment />} />
          </Route>
          <Route path='/reviewAudit' element={<ReviewAudit/>}/>
        </Routes>
      </BrowserRouter>

    </div>
  );
}

export default App;
