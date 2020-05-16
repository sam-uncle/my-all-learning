const http=require('http');

const PORT=8000
const server = http.createServer(require('../app'))

server.listen(PORT,()=>{
    console.log('listening to ' +PORT)
})