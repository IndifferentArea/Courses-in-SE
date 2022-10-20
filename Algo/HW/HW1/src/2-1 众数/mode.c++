#include<cstdio>
#include <iostream>
#include <cstring>
#include <algorithm>

using namespace std;
int a[100000], max_conts = 0;

void Mode(int a[], int l, int r, int& mode, int& times){
    if (r - l + 1 <= max_conts) return;
    int mid = l + r >> 1;
    int midnum = a[mid], i = mid, j = mid;
    // 计算众数长度
    while(a[i] == midnum && i >= l) i--;
    while(a[j] == midnum && j <= r) j++;
    if(max_conts < j - i - 1){
        max_conts = j - i - 1;
        mode = a[mid];
    }
    Mode(a, l, mid, mode, times);
    Mode(a, mid + 1, r, mode, times);
    times = max_conts;
    return;
}

int main()
{
    freopen("input.txt","r",stdin);
    freopen("output.txt","w",stdout);
    int n; cin >> n;
    for (int i = 0; i < n; i ++ ) cin >> a[i];
    sort(a, a+n);
    int m, t;
    Mode(a, 0, n-1, m, t);
    cout << m << endl << t;
}


