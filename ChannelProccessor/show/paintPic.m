file=dir('D:\Clanguge\tmp2\ChannelProccessor\preTrain\*.txt');
color = ['r','g','b','c','m','k','w'];
shape = ['.','+','*','x','^']
for n=1:length(file)
    n ,[color(mod((n-1),7)+1),shape(floor((n-1)/7)+1)]
    temp = importdata(['D:\Clanguge\tmp2\ChannelProccessor\preTrain\',file(n).name]);
    a = temp(:,2);
    b = temp(:,3);
    %c =temp(:,4);
    plot(a,b,[color(mod((n-1),7)+1),shape(floor((n-1)/7)+1)]);
    hold on
    if n > 30
        break
    end
end