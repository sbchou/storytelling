
var w = 960,
h = 500,
fill = d3.scale.category20();

var vis = d3.select("#chart").append("svg:svg")
.attr("width", w)
.attr("height", h);

var force = d3.layout.force()
.charge(-120)
.linkDistance(80)
.size([w, h]);

$('.dropdown-menu li a').click(function () {
	alert($(this).text());

	d3.json("test.json", function(json) {
	force
	.nodes(json.nodes)
	.links(json.links)
	.start();

	var link = vis.selectAll("line.link")
	.data(json.links)
	.enter().append("svg:line")
	.attr("class", "link")
	.style("stroke-width", function(d) { return Math.sqrt(d.value); })
	.attr("x1", function(d) { return d.source.x; })
	.attr("y1", function(d) { return d.source.y; })
	.attr("x2", function(d) { return d.target.x; })
	.attr("y2", function(d) { return d.target.y; });

	var node = vis.selectAll("text.node")
	.data(json.nodes) 
	.enter().append("svg:text")
	.attr("class", "node")
	.attr("x", function(d) { return d.x; })
	.attr("y", function(d) { return d.y; })
	.text(function(d) { return d.name; })
	.style("fill", "black")
	.style("font", function(d) { return Math.sqrt(d.size / 2) + "px sans-serif"; })
	.call(force.drag);

	force.on("tick", function() {
		link.attr("x1", function(d) { return d.source.x; })
		.attr("y1", function(d) { return d.source.y; })
		.attr("x2", function(d) { return d.target.x; })
		.attr("y2", function(d) { return d.target.y; });

		node.attr("x", function(d) { return d.x; })
		.attr("y", function(d) { return d.y; });
	});
});
});

