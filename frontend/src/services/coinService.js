import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

export const calculateMinCoins = async (targetAmount, coinDenominations) => {
    try {
        const response = await axios.post(`${API_URL}/api/min-coins`, {
            targetAmount,
            coinDenominations,
        });
        return response.data; // Return the result
    } catch (error) {
        console.error('Error fetching result from API:', error);
        throw error;
    }
};
