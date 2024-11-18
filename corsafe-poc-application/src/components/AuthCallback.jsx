import axios from 'axios';
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

const AuthCallback = () => {
    const navigate = useNavigate();
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');

    useEffect(() => {
        console.log("gooogle code : ", code);

        const handleAuthCallback = async () => {
            try {
                const response = await axios.post("http://localhost:8086/googlelogin", {
                    code: code,
                });
                console.log("login response : ",response);
                
                const data = response.data.result[0];
                if (data !== null) {
                    localStorage.setItem("jwtToken",data.jwtToken);
                    localStorage.setItem("refreshToken",data.refreshToken);
                    localStorage.setItem("userdata",data.userDetails);

                    toast.success("logged in successfully !!");
                    navigate('/business-profile');
                }
            } catch (error) {
                console.log("Failed to fetch response: ", error);
                toast.error("you are not registered user !!");
                navigate('/login');
            }
        };

        if (code) {
            handleAuthCallback();
        } else {
            toast.error("google code invalid !!");
            navigate('/login');

        }
    }, [code, navigate]);

    return (
        <div>
            <h2>loading...</h2>
        </div>
    );
};

export default AuthCallback;
