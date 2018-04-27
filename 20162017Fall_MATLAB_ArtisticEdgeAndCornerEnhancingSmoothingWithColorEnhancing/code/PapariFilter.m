function y = art(name, namesave, sgm, NS, q)

% Reading the image
img = double(imread(name))/255;

% Epsilon
% Reason of this parameter is that
% if the s_i of a sub-region is a zero,
% after the s_i's are computed the epsilon parameter will be added to
% s_i's.
epsilon = 10^-4;

% nr x nc x N image shape
[nr, nc, N] = size(img);

% Our Grid for Gaussian Kernel
[x,y] = meshgrid(linspace(-nc/2, nc/2, nc), linspace(nr/2, -nr/2, nr));

% Gaussian Kernel
gaux = exp( -(x.^2+y.^2) / (2*sgm^2) );

% fourirer transform I, I^2
for i = 1 : N
    imW(:,:,i)  = fft2(img(:,:,i));
    im2W(:,:,i) = fft2(img(:,:,i).^2);
end


num = zeros(nr,nc,N);
den = zeros(nr,nc);

for i = 0 : NS-1
    % Weighting functions(G)
    U_i = sector(nr,nc, i*2*pi/NS, pi/NS);
    V_i = smoothgaux(U_i, 1, 2.5);
    t = V_i  .* gaux;
    a = sum(t(:));
    w_i = t / a;
    w_i = fft2(w_i);
    
    % Calculating m_i and S_i --> for colored pics. 
    S = zeros(nr,nc);
    % product in fourier domain is conv. in spatial domain 
    for k = 1 : N
        m(:,:,k) = ifft2(w_i .* imW(:,:,k));
        S = S +    ifft2(w_i .* im2W(:,:,k)) - m(:,:,k).^2;
    end
    
    % Last calculations
    S = (S+epsilon).^(-q/2);
    den = den + S;
    for k = 1 : N
        num(:,:,k) = num(:,:,k) + m(:,:,k).*S;
    end
   
end

% back to rgb space
for k = 1 : N
    y(:,:,k) = fftshift(num(:,:,k) ./ den);
end

% writing the image
imwrite(y, namesave);