import { IHakedis } from 'app/shared/model/hakedis.model';
import { IStokTakip } from 'app/shared/model/stok-takip.model';
import { IPersonel } from 'app/shared/model/personel.model';
import { IBolge } from 'app/shared/model/bolge.model';

export const enum PersonelTuru {
    MAASLI = 'MAASLI',
    GOTURU = 'GOTURU',
    YEVMIYE = 'YEVMIYE'
}

export interface IEkip {
    id?: number;
    ad?: string;
    telefon?: string;
    eposta?: string;
    turu?: PersonelTuru;
    hakedis?: IHakedis[];
    stokTakips?: IStokTakip[];
    ekipSorumlu?: IPersonel;
    bolge?: IBolge;
    ekipPersonels?: IPersonel[];
}

export class Ekip implements IEkip {
    constructor(
        public id?: number,
        public ad?: string,
        public telefon?: string,
        public eposta?: string,
        public turu?: PersonelTuru,
        public hakedis?: IHakedis[],
        public stokTakips?: IStokTakip[],
        public ekipSorumlu?: IPersonel,
        public bolge?: IBolge,
        public ekipPersonels?: IPersonel[]
    ) {}
}
