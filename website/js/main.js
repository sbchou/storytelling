
var w = 960,
h = 600,
fill = d3.scale.category20();

var vis = d3.select("#chart").append("svg:svg")
.attr("width", w)
.attr("height", h);

var force = d3.layout.force()
.charge(-150)
.linkDistance(300)
.size([w, h]);

var data = {};

$('.dropdown-menu li a').click(function () {
	data = {nodes: [], links: []};
	remove(data);

	if ($(this).text() === 'The New York Times') {
		d3.csv("nytimes_top_20_keywords.csv", 
			function(d) {
				return {
					name: d.NAME,
					size: +d.SIZE
				}
			},
			function(counts) {
				data.nodes = counts;

				d3.csv("nytimes_connections.csv",
					function(d) {
						return {
							source: +d.SOURCE,
							target: +d.TARGET,
							value: +d.VALUE
						}
					},
					function(connections) {
						data.links = connections;

						start(data);
				});
		});
	} else if ($(this).text() === 'The Washington Post') {
		d3.csv("washingtonpost_top_20_keywords.csv", 
			function(d) {
				return {
					name: d.NAME,
					size: +d.SIZE
				}
			},
			function(counts) {
				data.nodes = counts;

				d3.csv("washingtonpost_connections.csv",
					function(d) {
						return {
							source: +d.SOURCE,
							target: +d.TARGET,
							value: +d.VALUE
						}
					},
					function(connections) {
						data.links = connections;

						start(data);
				});
		});
	} else if ($(this).text() === 'CNN') {
		d3.csv("cnn_top_20_keywords.csv", 
			function(d) {
				return {
					name: d.NAME,
					size: +d.SIZE
				}
			},
			function(counts) {
				data.nodes = counts;

				d3.csv("cnn_connections.csv",
					function(d) {
						return {
							source: +d.SOURCE,
							target: +d.TARGET,
							value: +d.VALUE
						}
					},
					function(connections) {
						data.links = connections;

						start(data);					
				});
		});
	}
});

var remove = function (data) {
	force
	.nodes(data.nodes)
	.links(data.links)
	.start();

	var link = vis.selectAll("line.link")
		.data(data.links)
		.exit().remove();

	var node = vis.selectAll("text.node")
		.data(data.nodes)
		.exit().remove();

}

var start = function (data) {
	force
	.nodes(data.nodes)
	.links(data.links)
	.start();

	var link = vis.selectAll("line.link")
		.data(data.links);
	link.enter().append("svg:line")
		.attr("class", "link")
		.style("stroke-width", function(d) { return Math.sqrt(d.value); })
		.attr("x1", function(d) { return d.source.x; })
		.attr("y1", function(d) { return d.source.y; })
		.attr("x2", function(d) { return d.target.x; })
		.attr("y2", function(d) { return d.target.y; });

	var node = vis.selectAll("text.node")
		.data(data.nodes);
	node.enter().append("svg:text")
		.attr("class", "node")
		.attr("x", function(d) { return d.x; })
		.attr("y", function(d) { return d.y; })
		.text(function(d) { return d.name; })
		.style("fill", "black")
		.style("font", function(d) { return Math.sqrt(d.size) * 2 + "px sans-serif"; })
		.call(force.drag);

	node.on('mouseover', function(d) {
		link.style('stroke', function(l) {
			if (d === l.source || d === l.target)
				return '#FF0000';
			else
				return '#999';
		});
	});

	node.on('mouseout', function() {
		link.style('stroke', '#999');
	});

	force.on("tick", function() {
		link.attr("x1", function(d) { return d.source.x; })
		.attr("y1", function(d) { return d.source.y; })
		.attr("x2", function(d) { return d.target.x; })
		.attr("y2", function(d) { return d.target.y; });

		node.attr("x", function(d) { return d.x; })
		.attr("y", function(d) { return d.y; });
	});
}