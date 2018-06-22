function wk = WK(g1,g2)
    [n1, m1] = size(g1);
    [n2, m2] = size(g2);
	if (n1 ~= 1 || n2 ~= 1 || m1 ~= m2) 
        disp(size(g1));
        disp(size(g2));
        error('Vectors must be the same length');
    end
	nvars = m1;
	ct = zeros(2,2);
	for i = 1:(nvars-1)
		for j = (i+1):nvars
			if g1(i) == g1(j)
				ii = 1;
            else
				ii = 2;
            end
			if g2(i) == g2(j)
				jj = 1;
            else
				jj = 2;
            end
			ct(ii,jj) = ct(ii,jj) + 1;
        end
    end
    disp(ct);
	weights = eye(2);
	rows = zeros(1,2);
	cols = zeros(1,2);
	for i = 1:2
		for j = 1:2
			rows(i) = rows(i) + ct(i,j);
			cols(i) = cols(i) + ct(j,i);
        end
    end
    %disp(rows);
    %disp(cols);
	po = 0.0;
	pe = 0.0;
	n = 0;
	for i = 1:2
		n = n + rows(i);
		for j = 1:2
			po = po + weights(i,j)*ct(i,j);
			pe = pe + weights(i,j)*rows(i)*cols(j);
        end
    end
	po = po / n;
	pe = pe / (n*n);
	if pe ~= 1
		wk = (po - pe) / (1.0 - pe);
    else
		wk = 1.0;
    end
end
