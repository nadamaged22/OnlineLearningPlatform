import express from 'express'
import bootstrap from './src/index.router.js'

const app = express()
const port = 7000

bootstrap(app,express)
app.listen(port,()=>console.log(`App Listening On Port ${port}`))
