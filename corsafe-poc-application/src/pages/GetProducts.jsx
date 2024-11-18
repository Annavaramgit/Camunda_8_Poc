import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { fetchProducts } from '../slice/ProductSlice';
import { Card, CardContent, Tooltip,Grid } from '@mui/material';
import '../assets/styles/getproducts.css';
import ShoppingCartOutlinedIcon from '@mui/icons-material/ShoppingCartOutlined';
import { Outlet } from 'react-router-dom';
import { postAddtocart } from '../slice/AddToCartSlice';
import { fetchCartItems } from '../slice/DisplayCartItemsSlice';
import { fetchTotalPrice } from '../slice/TotalPriceSlice';

const GetProducts = () => {
    const dispatch = useDispatch();
    const { products, status, error } = useSelector((state) => state.products);
    const userId = useSelector((state) => state.auth.userDetails?.userId);
    const token = useSelector((state) => state.auth.jwtToken);
    console.log("token: ",token);
    

    useEffect(() => {
        if (status === 'idle') {
            dispatch(fetchProducts());
        }
    }, [dispatch, status]);
    useEffect(() => {
        console.log("User ID:", userId);
    }, [userId]);

    const handleAddToCart = (product) => {
       
        
        const { productId,price,name,stockQuantity } = product;
        if (!productId || !userId || !token ) {
            console.error("Product ID or User ID or token is missing!");
            return;
        }
    
        console.log("Adding to cart - User ID:", userId, "Product ID:", productId , "token: ",token,price,name); 
             
      
        dispatch(postAddtocart({ userId, productId,token,stockQuantity })).then(() => {
           
            dispatch(fetchCartItems({ userId, token }));
            dispatch(fetchTotalPrice({userId,token}));
        })
        .catch((error) => {
            console.log("Failed to delete item:", error);
        });
        
    };
    if (status === 'loading') {
        return <p>Loading...</p>;
    }

    if (status === 'failed') {
        return <p>Error: {error}</p>;
    }

    return (
        <div className='product-container'>
            <Grid container spacing={2}>
                <Grid item xs={12} lg={8} className='grid-scroll'>
                    <p className='title'>List of Products</p>
                    <Grid container spacing={2} sx={{padding:5}}>
                    {products.map((product, index) => (
    <Grid item xs={12} sm={6} md={4} key={index}>
        <Card className="products-cards" elevation={0}>
            <CardContent sx={{ padding: 0 }}>
                <div className="image-container">
                    <img 
                        src={`data:image/jpeg;base64,${product.productImage}`} 
                        alt={product.name} 
                    />
                    <span 
                        className="cart-icon" 
                        style={{color: 'black'}}
                        onClick={() => handleAddToCart(product)}
                    >
                        <Tooltip title="Add to Cart">
                            <ShoppingCartOutlinedIcon/>
                        </Tooltip>
                    </span>
                </div>
                <hr />
                <p>{product.name}</p>
                <h4>{`Rs. ${product.price}`}
                    <span>&nbsp;</span>
                    <span style={{ color: "#689f38" }}>(25% off)</span>
                </h4>
            </CardContent>
        </Card>
    </Grid>
))}

                  </Grid>
                </Grid>
                <Grid item xs={12} lg={4}  >
                    <Outlet/>
                </Grid>
            </Grid>
        </div>
    );
};

export default GetProducts;
