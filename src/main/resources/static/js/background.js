	var canvas = document.getElementById("root")||init(id);
	canvas.width = canvas.parentNode.offsetWidth;
	canvas.height = canvas.parentNode.offsetHeight;

	var step = 0;

	var lines = ["rgba(0,222,255, 0.2)", "rgba(157,192,249, 0.2)", "rgba(0,168,255, 0.2)"];
function loop() {
	var ctx = canvas.getContext('2d');
	ctx.clearRect(0, 0, canvas.width, canvas.height);
	step++;
	// 画3个不同颜色的矩形
	for (var j = lines.length - 1; j >= 0; j--) {
		ctx.fillStyle = lines[j];
		// 每个矩形的角度都不同，每个之间相差45度
		var angle = (step + j * 45) * Math.PI / 180;
		var deltaHeight = Math.sin(angle) * 50;
		var deltaHeightRight = Math.cos(angle) * 50;
		ctx.beginPath();
		ctx.moveTo(0, canvas.height / 2 + deltaHeight);
		ctx.bezierCurveTo(canvas.width / 2, canvas.height / 2 + deltaHeight - 50, canvas.width / 2, canvas.height / 2 + deltaHeightRight - 50, canvas.width, canvas.height / 2 + deltaHeightRight);
		ctx.lineTo(canvas.width, canvas.height);
		ctx.lineTo(0, canvas.height);
		ctx.lineTo(0, canvas.height / 2 + deltaHeight);
		ctx.closePath();
		ctx.fill();
	}
	requestAnimFrame(loop);
}

window.requestAnimFrame = (function() {
	return window.requestAnimationFrame ||
		window.webkitRequestAnimationFrame ||
		window.mozRequestAnimationFrame ||
		function(callback) {
			window.setTimeout(callback, 1000 / 60);
		};
})();

