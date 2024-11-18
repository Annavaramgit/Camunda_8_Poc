import { Button, Card, CardContent, Grid, Tooltip } from '@mui/material';
import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import '../assets/styles/AddToCart.css';
import CloseSharpIcon from '@mui/icons-material/CloseSharp';
import RemoveSharpIcon from '@mui/icons-material/RemoveSharp';
import AddSharpIcon from '@mui/icons-material/AddSharp';
import { deleteCartItem } from '../slice/DeleteCartitem';
import { deleteFromCart } from '../slice/CartSlice';
import { changeQuantity } from '../slice/QuantitySlice';
import { reduceQuantity } from '../slice/QuantitySlice';
import { fetchCartItems } from '../slice/DisplayCartItemsSlice';
import { fetchTotalPrice } from '../slice/TotalPriceSlice';

const AddToCart = () => {
  const dispatch = useDispatch();
  const userId = useSelector((state) => state.auth.userDetails?.userId);
  const token = useSelector((state) => state.auth.jwtToken);
  const cartItems = useSelector((state) => state.carts.items || []);
  const loading = useSelector((state) => state.carts.loading);
  const error = useSelector((state) => state.carts.error);
  const totalPrice = useSelector((state) => state.totalprice?.totalPrice);

  const navigate = useNavigate();

  const handleClick = () => {
    navigate('/allProducts/address');
  };

  useEffect(() => {
    if (userId && token) {
      console.log("userId for user: ", userId, "token for user: ", token);
      dispatch(fetchCartItems({ userId, token }));
      dispatch(fetchTotalPrice({ userId, token }));
    }
  }, [userId, token, dispatch]);

  const deleteToCart = (item) => {

    const { productId } = item.product;
    const { cartItemId } = item;
    const { quantity } = item;

    if (!productId || !token) {
      console.error("Product ID, User ID, or token is missing!");
      return;
    }

    dispatch(deleteCartItem({ userId, productId, token, quantity }))
      .then(() => {
        dispatch(fetchTotalPrice({ userId, token }));
        dispatch(fetchCartItems({ userId, token }));
      })
      .catch((error) => {
        console.log("Failed to delete item:", error);
      });

    dispatch(deleteFromCart({ id: cartItemId }));
  };

  const increaseQuantity = (qty) => {
    const productId = qty.product.productId;


    if (!productId || !userId || !token) {
      console.error("Product ID, User ID, or token is missing!");
      return;
    }


    dispatch(changeQuantity({ productId, userId, token }))
      .then(() => {
        dispatch(fetchTotalPrice({ userId, token }));
        dispatch(fetchCartItems({ userId, token }));
      })
      .catch((error) => {
        console.log("Failed to change quantity:", error);
      });
  };

  const decreaseQuantity = async (qty) => {
    const productId = qty.product.productId;
    console.log("decrease productId : ", productId);

    if (!productId || !userId || !token) {
      console.error("Product ID, User ID, or token is missing!");
      return;
    }

    try {
      await dispatch(reduceQuantity({ productId, userId, token }));
      await dispatch(fetchTotalPrice({ userId, token }));
      await dispatch(fetchCartItems({ userId, token }));
    } catch (error) {
      console.log("Failed to update quantity:", error);
    }



  };

  return (
    <div className="addTocart-container">
      <Card sx={{ height: '100vh', backgroundColor: '#f5f5f5' }}>
        <CardContent>
          <h2>Shopping Cart</h2>
          {loading ? (
            <p>Loading...</p>
          ) : error ? (
            <p>Error: {error}</p>
          ) : cartItems.length === 0 ? (
            <p>Your cart is empty</p>
          ) : (
            <Grid container spacing={2} sx={{ display: 'flex', flexDirection: 'column' }}>
              {cartItems.map((item, index) => (
                <Grid item xs={12} sm={6} md={4} key={index}>
                  <Card sx={{ width: '440px', height: '100px' }}>
                    <CardContent>

                      <div className="cart-container">
                        <div className="img-container">
                          <img
                            src={`data:image/jpeg;base64,${item.product.productImage}`}
                            alt={item.title}
                            className="cart-img"
                          />
                          <div className="quantity">
                            <span onClick={() => decreaseQuantity(item)}>
                              <RemoveSharpIcon />
                            </span>
                            <span className="quantity-number">{item.quantity}</span>
                            <span onClick={() => increaseQuantity(item)}>
                              <AddSharpIcon />
                            </span>

                          </div>
                        </div>
                        <div className="headings">
                          <p>{item.product.name}</p>
                          <h4>{`Rs. ${item.product.price}`}</h4>
                        </div>
                        <span
                          style={{ color: 'red' }}
                          onClick={() => deleteToCart(item)}
                        >
                          <Tooltip title="Remove item">
                            <CloseSharpIcon />
                          </Tooltip>
                        </span>
                      </div>
                    </CardContent>
                  </Card>
                </Grid>
              ))}
            </Grid>
          )}
          <div className="price-details">
            {loading ? (
              <p>Loading ....</p>
            ) : error ? (
              <p>{error}</p>
            ) : totalPrice !== null ? (
              <p className="price">Total Amount: Rs. {totalPrice}</p>
            ) : (
              <p>No total price available</p>
            )}
          </div>
          <br />
          <Button onClick={handleClick}>Continue</Button>
        </CardContent>
      </Card>
    </div>
  );
};

export default AddToCart;
