function plotCrankForces(dy,weight)
mass = weight/32.174;
w = linspace(0,rpm2radps(5000),300);
F = mass.*w.^2.*(dy/12);
plot(w,F,'b-');
title('Crankshaft centripetal force vs angular speed');
xlabel('Angular speed (rad/s)');
ylabel('Centripetal Force (lb)');
hold on;
plot([0,w(end)],[2965,2965],'r-');
hold off;
axis square;
end
function radps = rpm2radps(rpm)
radps = rpm*0.10471975511965999;
end