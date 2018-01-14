function y = smoothgaux(img, sgm, n);

U = ceil(sgm*n);
g = exp(-[-U:U].^2/2/sgm^2); g = g/sum(g);

x = zeros(size(img));
for i = -U:U
    x = x + circshift(img, [i,0,0]) *g(i+U+1);
end

y = zeros(size(img));
for i = -U:U
    y = y + circshift(x, [0,i,0]) *g(i+U+1);
end
