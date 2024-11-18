import {
    REGISTER_USER_REQUEST,
    REGISTER_USER_SUCCESS,
    REGISTER_USER_FAILURE,
} from './actionTypes';


export const registerUserRequest = () => ({
    type: REGISTER_USER_REQUEST,
});


export const registerUserSuccess = (data) => ({
    type: REGISTER_USER_SUCCESS,
    payload: data,
});


export const registerUserFailure = (error) => ({
    type: REGISTER_USER_FAILURE,
    payload: error,
});


export const registerUser = (registrationData) => {
    console.log("registerActions registrationData : ", registrationData);
    return (dispatch) => {
        dispatch(registerUserRequest());
        fetch('http://localhost:8086/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(registrationData),
        })
            .then(async response => {
                console.log("response status", response.status);
                if (!response.ok) {
                    const errorText = await response.text();
                    throw new Error('Registration failed: ' + errorText);
                }
                return response.json();
            })
            .then(data => {
                if (data.result && data.result.length > 0) {
                    const combinedData = {
                        ...data.result,
                        questionId: registrationData.questionId,
                        answer: registrationData.answer,
                    };
                    console.log("RESPONSE DATA",data);
                    dispatch(registerUserSuccess(combinedData));
                    console.log("dispatched data", combinedData);
                } else {
                    dispatch(registerUserFailure('No result found.'));
                }
            })
            .catch(error => {
                dispatch(registerUserFailure(error.message));
                console.error("Error:", error);
            });
    };
};


