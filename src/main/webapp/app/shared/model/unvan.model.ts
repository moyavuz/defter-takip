export interface IUnvan {
    id?: number;
    ad?: string;
    aciklama?: string;
    aktifMi?: boolean;
}

export class Unvan implements IUnvan {
    constructor(public id?: number, public ad?: string, public aciklama?: string, public aktifMi?: boolean) {
        this.aktifMi = this.aktifMi || false;
    }
}
