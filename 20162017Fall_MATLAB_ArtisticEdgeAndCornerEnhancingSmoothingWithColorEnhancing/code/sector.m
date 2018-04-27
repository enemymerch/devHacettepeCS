function S = sector(nr, nc, a, wd)

[x,y] = meshgrid(linspace(-nc/2, nc/2, nc), linspace(nr/2, -nr/2, nr));

S = (x*cos(a-wd+pi/2) + y*sin(a-wd+pi/2) > 0) .* (x*cos(a+wd+pi/2) + y*sin(a+wd+pi/2) <= 0);
