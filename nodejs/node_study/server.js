var http = require('http');

var handler = function(req, res){
	res.end("server ....");
};

var server = http.createServer(handler);

server.listen(8090);

console.log('server listen port : 8090....');

console.log('console log ......');
console.log(__filename);

// var t = setTimeout(function(){
// 	console.log(__dirname)
// }, 20000);
// clearTimeout(t);
// var tt = setInterval(function(){
// 	console.log(__dirname);
// }, 2000);

// setTimeout(function(){
// 	clearInterval(tt);
// }, 10000);

// console.time(tt);
// console.timeEnd(tt);
console.trace();