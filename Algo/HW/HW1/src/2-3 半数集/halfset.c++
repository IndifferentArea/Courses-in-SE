#include<cstdio>
#include <iostream>
#include <cstring>
#include <algorithm>

using namespace std;

int HalfSet(int n){
    if(n == 1) return 1;
    int sum = 0;
    for(int i = 1; i <= n/2; i++) sum += HalfSet(i);
    return sum + 1;
}

int main()
{
    freopen("input.txt","r",stdin);
    freopen("output.txt","w",stdout);
    int n; cin >> n;
    cout << HalfSet(n);
    return 0;
}