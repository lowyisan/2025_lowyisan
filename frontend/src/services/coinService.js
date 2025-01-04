import axios from 'axios';

const API_URL = 'http://140.245.122.206';

// Function to call the backend API
export const calculateMinCoins = async (targetAmount, coinDenominations) => {
    try {
        const response = await axios.post(`${API_URL}/api/min-coins`, {
            targetAmount,
            coinDenominations,
        });
        return response.data; // Return the result
    } catch (error) {
        console.error('Error fetching result from API:', error);
        throw error; // Pass error to the caller
    }
};
