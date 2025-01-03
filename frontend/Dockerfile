FROM node:16

# Set working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package.json package-lock.json ./

# Install dependencies with --legacy-peer-deps
RUN npm install --legacy-peer-deps

# Copy the entire frontend codebase
COPY . .

# Expose the frontend port
EXPOSE 3000

# Start the React application
CMD ["npm", "start"]
