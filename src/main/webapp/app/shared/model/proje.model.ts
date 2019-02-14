import { Moment } from 'moment';
import { IProjeTuru } from 'app/shared/model/proje-turu.model';
import { IBolge } from 'app/shared/model/bolge.model';
import { IIscilik } from 'app/shared/model/iscilik.model';

export const enum GorevDurumu {
    BEKLIYOR = 'BEKLIYOR',
    HAZIR = 'HAZIR',
    CALISILIYOR = 'CALISILIYOR',
    TAMAMLANDI = 'TAMAMLANDI'
}

export const enum OnemDurumu {
    ACIL = 'ACIL',
    IVEDI = 'IVEDI',
    NORMAL = 'NORMAL'
}

export interface IProje {
    id?: number;
    isTanimi?: string;
    aciklama?: string;
    detay?: any;
    durumu?: GorevDurumu;
    onemDurumu?: OnemDurumu;
    tarih?: Moment;
    baslamaTarihi?: Moment;
    bitisTarihi?: Moment;
    turu?: IProjeTuru;
    bolge?: IBolge;
    isciliks?: IIscilik[];
}

export class Proje implements IProje {
    constructor(
        public id?: number,
        public isTanimi?: string,
        public aciklama?: string,
        public detay?: any,
        public durumu?: GorevDurumu,
        public onemDurumu?: OnemDurumu,
        public tarih?: Moment,
        public baslamaTarihi?: Moment,
        public bitisTarihi?: Moment,
        public turu?: IProjeTuru,
        public bolge?: IBolge,
        public isciliks?: IIscilik[]
    ) {}
}
