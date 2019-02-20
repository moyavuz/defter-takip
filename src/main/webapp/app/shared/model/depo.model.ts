import { IStokTakip } from 'app/shared/model/stok-takip.model';
import { IPersonel } from 'app/shared/model/personel.model';

export const enum DepoTuru {
    MERKEZ_DEPO = 'MERKEZ_DEPO',
    BOLGE_DEPO = 'BOLGE_DEPO'
}

export interface IDepo {
    id?: number;
    ad?: string;
    adres?: string;
    turu?: DepoTuru;
    stokTakips?: IStokTakip[];
    sorumlu?: IPersonel;
}

export class Depo implements IDepo {
    constructor(
        public id?: number,
        public ad?: string,
        public adres?: string,
        public turu?: DepoTuru,
        public stokTakips?: IStokTakip[],
        public sorumlu?: IPersonel
    ) {}
}
