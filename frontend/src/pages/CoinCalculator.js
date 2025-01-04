import React, { useState } from 'react';
import { calculateMinCoins } from '../services/coinService';
import '../styles/CoinCalculator.css'; 

const CoinCalculator = () => {
    const [targetAmount, setTargetAmount] = useState('');
    const [coinDenominations, setCoinDenominations] = useState('');
    const [result, setResult] = useState([]);
    const [errorMessage, setErrorMessage] = useState(''); 

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrorMessage(''); 
        setResult([]);

        const denominations = coinDenominations.split(',').map(Number);

        try {
            const result = await calculateMinCoins(parseFloat(targetAmount), denominations);
            setResult(result);
        } catch (error) {
            console.error('Error fetching result:', error);

            // Display error messages returned by the backend
            if (error.response && error.response.data) {
                setErrorMessage(error.response.data || 'Unable to calculate. Please check your inputs.');
            } else {
                setErrorMessage('Unable to calculate. Ensure inputs are correct.');
            }
        }
    };

    return (
        <div className="calculator-container">
            <div className="calculator-card">
                <h1 className="calculator-title">💰 Coin Calculator</h1>
                <form className="calculator-form" onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="targetAmount">Target Amount</label>
                        <input
                            id="targetAmount"
                            type="number"
                            placeholder="Enter target amount (e.g., 7.03)"
                            value={targetAmount}
                            onChange={(e) => setTargetAmount(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="coinDenominations">Coin Denominations</label>
                        <input
                            id="coinDenominations"
                            type="text"
                            placeholder="Enter coin denominations (comma-separated)"
                            value={coinDenominations}
                            onChange={(e) => setCoinDenominations(e.target.value)}
                            required
                        />
                    </div>
                    <button type="submit" className="submit-button">
                        Calculate
                    </button>
                </form>
                <div className="result-section">
                    <h3>Result:</h3>
                    {result.length > 0 ? (
                        <p className="result-output">{result.join(', ')}</p>
                    ) : (
                        !errorMessage && <p className="no-result">No result yet.</p>
                    )}
                    {errorMessage && (
                        <div className="error-message">
                            <p>{errorMessage}</p>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default CoinCalculator;
