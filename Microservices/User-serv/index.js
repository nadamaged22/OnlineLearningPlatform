import express from 'express'
import bootstrap from './src/index.router.js'
import cors from 'cors';

const app = express()
const port = 3001

bootstrap(app,express)
app.listen(port,()=>console.log(`App Listening On Port ${port}`))
