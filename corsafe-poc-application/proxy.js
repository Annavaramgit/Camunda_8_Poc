const express = require('express');
const { createProxyMiddleware } = require('http-proxy-middleware');
const app = express();

app.use((req, res, next) => {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
  res.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
  

  if (req.method === 'OPTIONS') {
    return res.status(200).json({});
  }

  next();
});

app.use(
  '/api', 
  createProxyMiddleware({
    target: 'https://syd-1.connectors.camunda.io', 
    changeOrigin: true,
    pathRewrite: {
      '^/api': '',
    },
  })
);

app.listen(5000, () => {
  console.log('Proxy server is running on http://localhost:5000');
});