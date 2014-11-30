$.dateFromString = function(str) {
	var a = $.map(str.split(/[^0-9]/), function(s) {
		return parseInt(s, 10)
	});
	return new Date(a[0], a[1] - 1 || 0, a[2] || 1, a[3] || 0, a[4] || 0,
			a[5] || 0, a[6] || 0);
}
